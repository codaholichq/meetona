package meetona.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.entity.User;
import meetona.core.exception.AppException;
import meetona.core.exception.SignupException;
import meetona.core.interfaces.IUserService;
import meetona.core.payload.request.UserRequest;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UserDto;
import meetona.data.mapper.GeneralMapper;
import meetona.data.messaging.producers.UserActionProducer;
import meetona.data.repository.MemberRepository;
import meetona.data.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final GeneralMapper generalMapper;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserActionProducer userActionProducer;

    @Override
    @Cacheable("users")
    public ApiResponse<List<UserDto>> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        List<UserDto> userDto = users.stream()
                .map(generalMapper::toDto)
                .toList();

        ApiResponse<List<UserDto>> response = new ApiResponse<>(userDto, true);

        log.info("Fetched units => {}", userDto);
        return response;
    }

    @Override
    @Cacheable("user")
    public ApiResponse<UserDto> getById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);

        User user = userOptional.orElse(null); // Unwrap the Optional to get a Unit or null

        UserDto userDto = generalMapper.toDto(user);

        var response = new ApiResponse<>(userDto, true);

        log.info("Fetched unit => {}", userDto);
        return response;
    }

    @Transactional
    public ApiResponse<UserDto> add(UserRequest userRequest) {
        boolean isUsernameExists = userRepository.existsByUsername(userRequest.username());

        if (isUsernameExists) {
            throw new SignupException(userRequest.username(), "Username already exists");
        }


        User newUser = buildUser(userRequest);
        userRepository.save(newUser);

        UserDto userDto = generalMapper.toDto(newUser);

        var response = new ApiResponse<>(userDto, true);

        userActionProducer.sendMessage(newUser);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "user", key = "#user.id")
    public ApiResponse<UserDto> update(UUID id, UserRequest request) {
        boolean isUserExists = userRepository.existsById(id);

        if(isUserExists) {
            throw new AppException("Id does not exists");
        }

        User newUser = buildUser(request);

        userRepository.save(newUser);
        UserDto updatedUser = generalMapper.toDto(newUser);

        var response = new ApiResponse<>(updatedUser, true);

        userActionProducer.sendMessage(updatedUser);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "user", key = "#id")
    public ApiResponse<UserDto> delete(UUID id) {
        boolean isUserExists = userRepository.existsById(id);

        if(isUserExists){
            throw new AppException("Id does not exists");
        }

        userRepository.deleteById(id);
        UserDto deletedUser = new UserDto(id, null, null, null, null, null);

        var response = new ApiResponse<>(deletedUser, true);

        userActionProducer.sendMessage(deletedUser);
        return response;
    }

    private User buildUser(UserRequest request) {
        var member = memberRepository
                .findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException(request.memberId() + " does not exist"));

        return User.builder()
                .username(request.username())
                .roles(request.roles())
                .isEmailVerified(false)
                .member(member)
                .password(passwordEncoder.encode(request.password()))
                .build();
    }
}
