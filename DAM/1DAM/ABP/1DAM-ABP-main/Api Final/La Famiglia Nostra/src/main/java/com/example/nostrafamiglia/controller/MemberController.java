package com.example.nostrafamiglia.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.nostrafamiglia.dto.MemberDTO;
import com.example.nostrafamiglia.model.Member;
import com.example.nostrafamiglia.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/lafamiglia/members")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() { return ResponseEntity.ok(memberService.getAllMembersDTO()); }

    // id
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // guardar
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member newMember = memberService.saveMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }
    // actu
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable int id, @RequestBody Member member) {
        if (!memberService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        member.setIdMiembro(id); // si eñ id es ok
        return ResponseEntity.ok(memberService.saveMember(member));
    }
    //Elimina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable int id) {
        if (!memberService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

}
