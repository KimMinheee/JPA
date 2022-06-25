package JpaShop_.JpaShop_.api;


import JpaShop_.JpaShop_.domain.Member;
import JpaShop_.JpaShop_.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /*
    * [POST] : 회원 등록 v1 : 요청값으로 Member 엔티티를 직접 받는다.
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }


    /*
     * [POST] :  회원 등록 v2 : 요청값으로 CreateMemberRequest DTO 생성
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.name);
        Long id =memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /*
     * [PUT] : 회원 수정 v2
     */
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,  @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName()); //수정
        Member findMember = memberService.findOne(id); //조회
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }


    /*
    * [GET] - 회원 조회 : entity 리스트를 반환.
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    /*
     * [GET] - 회원 조회 :
     */
    @GetMapping("/api/v2/members")
    public Result membersV2(){
        List<Member> findMembers = memberService.findMembers();

        //엔티티 -> dto로 변환
        List<MemberDto> collect = findMembers.stream()
                .map(m-> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    //생성
    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;
        public CreateMemberResponse(Long id){
            this.id=id;
        }
    }

    //수정
    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    //조회
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


}
