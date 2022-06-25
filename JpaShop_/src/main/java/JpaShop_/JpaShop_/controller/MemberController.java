package JpaShop_.JpaShop_.controller;

import JpaShop_.JpaShop_.domain.Address;
import JpaShop_.JpaShop_.domain.Member;
import JpaShop_.JpaShop_.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value="/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value="/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm"; //이때 form객체 그대로 들고 다시 감.
        }
        Address address = new Address(form.getCity(),form.getStreet(),form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);

        return "redirect:/"; //성공시 home(/)으로 redirect
    }
    @GetMapping(value="/members")
    public String list(Model model){
        List<Member> members= memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
