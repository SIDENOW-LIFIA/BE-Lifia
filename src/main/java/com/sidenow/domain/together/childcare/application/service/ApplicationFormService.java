package com.sidenow.domain.together.childcare.application.service;

import com.sidenow.domain.together.childcare.application.dto.req.ApplicationFormRequest;
import com.sidenow.domain.together.childcare.application.dto.res.ApplicationFormResponse.ApplicationFormCreateResponse;

public interface ApplicationFormService {

    ApplicationFormCreateResponse createApplicationForm(Long childcareId, ApplicationFormRequest.ApplicationFormCreateRequest req);

}
