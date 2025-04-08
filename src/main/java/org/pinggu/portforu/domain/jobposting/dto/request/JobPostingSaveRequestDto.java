package org.pinggu.portforu.domain.jobposting.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
