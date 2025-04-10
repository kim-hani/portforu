package org.pinggu.portforu.domain.jobposting.repository;

import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    Page<JobPosting> findAllByDeletedAtIsNull(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE JobPosting j SET " +
            "j.name = COALESCE(:name, j.name), " +
            "j.industry = COALESCE(:industry, j.industry), " +
            "j.address = COALESCE(:address, j.address), " +
            "j.salary = COALESCE(:salary, j.salary), " +
            "j.qualifications = COALESCE(:qualifications, j.qualifications), " +
            "j.preferential = COALESCE(:preferential, j.preferential), " +
            "j.closingDate = COALESCE(:closingDate, j.closingDate) " +
            "WHERE j.id = :id")
    Integer updateJobPosting(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("industry") String industry,
            @Param("address") String address,
            @Param("salary") String salary,
            @Param("qualifications") String qualifications,
            @Param("preferential") String preferential,
            @Param("closingDate") String closingDate
    );

}
