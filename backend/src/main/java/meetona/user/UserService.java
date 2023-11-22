package meetona.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.member.MemberRepository;
import meetona.role.Role;
import meetona.role.RoleRepository;
import meetona.shared.enums.AppRole;
import meetona.shared.exception.InsertionFailedException;
import meetona.shared.exception.LoginException;
import meetona.shared.exception.ResourceNotFoundException;
import meetona.shared.response.ServiceResponse;
import meetona.user.dtos.AuthDto;
import meetona.user.dtos.UserDto;
import meetona.user.dtos.UserRequest;
import meetona.user.security.TokenProvider;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final UserMapper mapper;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserActionProducer userActionProducer;
    private final AuthenticationManager authenticationManager;

    @Override
    public ServiceResponse<UserDto> authenticate(AuthDto authDto) {
        var response = new ServiceResponse<UserDto>();
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authDto.username(),
                authDto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createToken(authentication);

        User user = (User) authentication.getPrincipal();

        if (user == null) throw new LoginException("User not found");

        UserDto userDto = mapper.toDto(user).setAccessToken(accessToken);
        response.setData(userDto, true);

        userActionProducer.sendMessage(userDto);
        return response;
    }

    @Override
    @Cacheable("users")
    public ServiceResponse<List<UserDto>> getAll(Pageable pageable) {
        var response = new ServiceResponse<List<UserDto>>();
        Page<User> users = userRepository.findAll(pageable);

        List<UserDto> userDto = users.stream()
                .map(mapper::toDto)
                .toList();

        response.setData(userDto, true);

        log.info("Fetched users => {}", userDto);
        return response;
    }

    @Override
    @Cacheable("user")
    public ServiceResponse<UserDto> getById(UUID id) {
        var response = new ServiceResponse<UserDto>();
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        UserDto userDto = mapper.toDto(user);

        response.setData(userDto, true);

        log.info("Fetched user => {}", userDto);
        return response;
    }

    @Transactional
    public ServiceResponse<UserDto> add(UserRequest userRequest) {
        var response = new ServiceResponse<UserDto>();
        boolean isUsernameExists = userRepository.existsByUsername(userRequest.username());

        if (isUsernameExists) {
            throw new InsertionFailedException(userRequest.username(), " already exists");
        }

        User newUser = buildUser(userRequest);
        userRepository.save(newUser);

        UserDto userDto = mapper.toDto(newUser);
        response.setData(userDto, true);

        userActionProducer.sendMessage(userDto);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "user", key = "#user.id")
    public ServiceResponse<UserDto> update(UUID id, UserRequest userRequest) {
        var response = new ServiceResponse<UserDto>();
        boolean isUserExists = userRepository.existsById(id);

        if(!isUserExists) {
            throw new ResourceNotFoundException("User", "id", id);
        }

        User newUser = buildUser(userRequest);

        userRepository.save(newUser);
        UserDto updatedUser = mapper.toDto(newUser);

        response.setData(updatedUser, true);

        userActionProducer.sendMessage(updatedUser);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "user", key = "#id")
    public ServiceResponse<UserDto> delete(UUID id) {
        var response = new ServiceResponse<UserDto>();
        var user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new ResourceNotFoundException("User", "id", id);
        }

        userRepository.deleteById(id);

        var userDto = mapper.toDto(user.get());

        response.setData(userDto, true);

        userActionProducer.sendMessage(userDto);
        return response;
    }

    private User buildUser(UserRequest request) {
        var member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", request.memberId()));

        Set<Role> roles = request.roles().stream()
                .map(role -> roleRepository.findByName(AppRole.valueOf(role)))
                .collect(Collectors.toSet());

        return User.builder()
                .username(request.username())
                .email(request.email())
                .roles(roles)
                .isEmailVerified(false)
                .member(member)
                .password(passwordEncoder.encode(request.password()))
                .build();
    }
}
