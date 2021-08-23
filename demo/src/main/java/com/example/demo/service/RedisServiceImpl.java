package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.RedisRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@Service
public class RedisServiceImpl implements RedisService {

    private final RedisRepository redisRepository;

    @PostConstruct
    public void postConstruct() {
        getStudent();
    }

    public void saveStudent() {
        Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        redisRepository.save(student);

        log.info("Save into redis for student {}", student);
    }

    public Student getStudent() {
        String id = "Eng2015001";
        Student retrievedStudent = redisRepository.findById(id).get();

        log.info("Retrieve from redis for ID {} and result {}", id, retrievedStudent);

        return retrievedStudent;
    }
}
