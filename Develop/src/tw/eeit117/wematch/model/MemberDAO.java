package tw.eeit117.wematch.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("MemberDAO")
public class MemberDAO {

	private Member member;
	private SessionFactory sessionFactory;

	@Autowired
	public MemberDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean checkLogin(Member users) {
		Session session = sessionFactory.getCurrentSession();
		Member resultAccount = selectMember(users.getMemberAccount(), users.getMemberPwd());

		if (resultAccount != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean insertMember(String memberAccount, String memberPwd) {
		Session session = sessionFactory.getCurrentSession();
		Member resultAccount = selectMember(memberAccount, memberPwd);

		if (resultAccount != null) {
			return false;
		} else {
			Member member = new Member();
			member.setMemberAccount(memberAccount);
			member.setMemberPwd(memberPwd);
			Serializable identifier = session.save(member);
			System.out.println("identifier:" + identifier);

			return true;
		}
	}

	public Member selectMember(String memberAccount, String memberPwd) {
		Session session = sessionFactory.getCurrentSession();

		String hqlstr = "From Member where memberAccount=:user and memberPwd=:pwd";
		Query<Member> query = session.createQuery(hqlstr, Member.class);
		query.setParameter("user", memberAccount);
		query.setParameter("pwd", memberPwd);
		Member resultAccount = query.uniqueResult();

		return resultAccount;
	}

	public List<Member> selectAllMember() {
		Session session = sessionFactory.getCurrentSession();

		Query<Member> query = session.createQuery("From Member", Member.class);
		List<Member> resultAccount = query.getResultList();
		return resultAccount;
	}

	public void updateMember(Member member) {
		Session session = sessionFactory.getCurrentSession();

		String hqlstr = "Update Member set memberName=:name, nickname=:nickname, gender=:gender, memberMail=:mail, birthdayDate=:birthday, starSign=:starSign, city=:city, bloodType=:bloodType, hobbies=:hobbies, selfIntro=:selfIntro where memberId=:id";
		Query<Member> query = session.createQuery(hqlstr, Member.class);
		query.setParameter("name", member.getMemberName());
		query.setParameter("nickname", member.getNickname());
		query.setParameter("gender", member.getGender());
		query.setParameter("mail", member.getMemberEmail());
		query.setParameter("birthday", member.getBirthdayDate());
		query.setParameter("starSign", member.getStarSign());
		query.setParameter("city", member.getCity());
		query.setParameter("bloodType", member.getBloodType());
		query.setParameter("hobbies", member.getHobbies());
		query.setParameter("selfIntro", member.getSelfIntro());
		query.setParameter("id", member.getMemberId());
		
		session.save(query);
	}

	public void deleteMember(Integer memberId) {
		Session session = sessionFactory.getCurrentSession();

		String hqlstr = "From Member where memberId=:mId";
		Query<Member> query = session.createQuery(hqlstr, Member.class);
		query.setParameter("mId", memberId);

		Member resultAccount = query.uniqueResult();
		session.delete(resultAccount);
	}
}
