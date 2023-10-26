package com.sidenow.domain.member.service;

import com.sidenow.domain.freeboard.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.freeboard.board.dto.res.FreeBoardResponse.FreeBoardGetResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberInfoResponse;
import org.springframework.data.domain.Page;

public interface MemberMyPageService {
    MemberInfoResponse getMemberInfo();
}
