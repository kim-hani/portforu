package org.pinggu.portforu.domain.jobposting.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class JobPostingSaveRequestDto {

    private String name;

    private String industry;

    private String address;

    private String salary;

    private String qualifications;

    private String preferential;

    private Instant postingDate;

    private Instant closingDate;

}
