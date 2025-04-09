package org.pinggu.portforu.domain.jobposting.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobPostingSaveRequestDto {

    private String name;

    private String industry;

    private String address;

    private String salary;

    private String qualifications;

    private String preferential;

    private String postingDate;

    private String closingDate;

}
