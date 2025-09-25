package com.example.nostrafamiglia.service;

import com.example.nostrafamiglia.dto.ArmaDTO;
import com.example.nostrafamiglia.dto.JefeDTO;
import com.example.nostrafamiglia.dto.MemberDTO;
import com.example.nostrafamiglia.dto.RangoDTO;
import com.example.nostrafamiglia.model.Member;
import com.example.nostrafamiglia.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    //get completo
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    //get dto
    public List<MemberDTO> getAllMembersDTO() {
        return memberRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private MemberDTO convertToDTO(Member m) {
        return new MemberDTO(
                m.getIdMiembro(),
                capitalize(m.getFirstName()),
                capitalize(m.getLastName()),
                capitalize(m.getApodo()),
                (m.getArma() != null)
                        ? new ArmaDTO(
                        m.getArma().getIdArma(),
                        m.getArma().getNombreArma(),
                        m.getArma().getTipo(),
                        m.getArma().getPrecio())
                        : null,
                (m.getRango() != null)
                        ? new RangoDTO(m.getRango().getIdRango(), m.getRango().getRango())
                        : null,
                (m.getJefe() != null)
                        ? new JefeDTO(
                        m.getJefe().getFirstName(),
                        m.getJefe().getLastName(),
                        m.getJefe().getApodo())
                        : null
        );
    }

    //id
    public Optional<Member> getMemberById(int id) {
        return memberRepository.findById(id);
    }
    // formato para los nombres y apodos
    public Member saveMember(Member member) {
        member.setFirstName(capitalize(member.getFirstName()));
        member.setLastName(capitalize(member.getLastName()));
        if (member.getApodo() != null) {
            member.setApodo(capitalize(member.getApodo()));
        }
        return memberRepository.save(member);
    }
    // metodo del formato de texto
    private String capitalize(String texto) {
        if (texto == null || texto.isBlank()) return texto; // si no hay texto que formatear no hace nada para evitar que genere errores
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase(); //la primera en mayus y las siguiente en minus
    }

    public void deleteMember(int id) {
        memberRepository.deleteById(id);
    }
    public boolean existsById(int id) {
        return memberRepository.existsById(id);
    }
}
