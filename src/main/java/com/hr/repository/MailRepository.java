package com.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hr.entity.Mail;

@Repository
public interface MailRepository extends JpaRepository<Mail, Integer>{

}
