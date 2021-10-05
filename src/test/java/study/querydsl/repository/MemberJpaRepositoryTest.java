package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void basicTest(){
        Member member1 = new Member("member1", 10);
        memberJpaRepository.save(member1);

        Member findMember = memberJpaRepository.findById(member1.getId()).get();
        assertThat(findMember).isEqualTo(member1);

        List<Member> findAll = memberJpaRepository.findAll();
        assertThat(findAll.get(0)).isEqualTo(findMember);

        List<Member> result1 = memberJpaRepository.findByUsername("member1");
        assertThat(result1.get(0)).isEqualTo(member1);
    }

    @Test
    public void basicTest_querydsl(){
        Member member1 = new Member("member1", 10);
        memberJpaRepository.save(member1);

        Member findMember = memberJpaRepository.findById(member1.getId()).get();
        assertThat(findMember).isEqualTo(member1);

        List<Member> findAll = memberJpaRepository.findAll_Querydsl();
        assertThat(findAll.get(0)).isEqualTo(findMember);

        List<Member> result1 = memberJpaRepository.findByUsername_Querydsl("member1");
        assertThat(result1.get(0)).isEqualTo(member1);
    }

    @Test
    public void searchBuilderTest(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
        memberSearchCondition.setAgeGoe(32);
        memberSearchCondition.setAgeLoe(41);
        memberSearchCondition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(memberSearchCondition);
        assertThat(result).extracting("username").containsExactly("member4");
    }

    @Test
    public void searchTest(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
        memberSearchCondition.setAgeGoe(32);
        memberSearchCondition.setAgeLoe(41);
        memberSearchCondition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.search(memberSearchCondition);
        assertThat(result).extracting("username").containsExactly("member4");
    }

    @Test
    public void searchTest2(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
        memberSearchCondition.setAgeGoe(32);
        memberSearchCondition.setAgeLoe(41);
        memberSearchCondition.setTeamName("teamB");

        List<MemberTeamDto> result = memberRepository.search(memberSearchCondition);
        assertThat(result).extracting("username").containsExactly("member4");
    }

    @Test
    public void searchPageSimple(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
        int page = 1;
        int size = 2;
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<MemberTeamDto> result = memberRepository.searchPageSimple(memberSearchCondition, pageRequest);
        for (MemberTeamDto memberTeamDto : result) {
            System.out.println("memberTeamDto = " + memberTeamDto);
        }
        assertThat(result.getSize()).isEqualTo(2);
    }

    @Test
    public void searchPageComplex(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
        int page = 1;
        int size = 2;
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<MemberTeamDto> result = memberRepository.searchPageSimple(memberSearchCondition, pageRequest);
        for (MemberTeamDto memberTeamDto : result) {
            System.out.println("memberTeamDto = " + memberTeamDto);
        }
        assertThat(result.getSize()).isEqualTo(2);
    }
}