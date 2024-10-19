
package com.test.charity_api.repository;

import com.test.charity_api.entity.Lecturer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Lecturer l SET l.status = false WHERE l.id = :id")
    void deleteLecturer(@Param("id") int id);
    
    Page<Lecturer> findByStatusTrueAndNameContaining(String query, Pageable pageable);

    
    Lecturer findByStatusTrueAndId(int id);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Campaign c WHERE c.lecturer.id = :lecturerId AND c.closeDate > CURRENT_DATE")
    boolean lecturerIsHavingAnActiveCampain(@Param("lecturerId") int lecturerId);
}
