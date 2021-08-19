package com.company.freeks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, String> {
}
