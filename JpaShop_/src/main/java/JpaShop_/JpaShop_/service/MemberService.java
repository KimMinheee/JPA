package JpaShop_.JpaShop_.service;

import JpaShop_.JpaShop_.domain.Member;
import JpaShop_.JpaShop_.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    //@RequiredArgsConstructor annotation사용해서 final키워드 붙은거 인젝션 주입함.
    private final MemberRepository memberRepository;

    /*
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원 검사
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
     * 개별 회원 조회(id이용)
     * */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
