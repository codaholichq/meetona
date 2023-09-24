package meetona.data.mapper;

import meetona.core.entity.Member;
import meetona.core.payload.response.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDto ToMemberDto(Member member);
}
