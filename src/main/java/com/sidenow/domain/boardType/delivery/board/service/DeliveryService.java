package com.sidenow.domain.boardType.delivery.board.service;

import com.sidenow.domain.boardType.delivery.board.dto.req.DeliveryRequest.DeliveryCreateRequest;
import com.sidenow.domain.boardType.delivery.board.dto.req.DeliveryRequest.DeliveryUpdateRequest;
import com.sidenow.domain.boardType.delivery.board.dto.res.DeliveryResponse.AllDeliverys;
import com.sidenow.domain.boardType.delivery.board.dto.res.DeliveryResponse.DeliveryCreateResponse;
import com.sidenow.domain.boardType.delivery.board.dto.res.DeliveryResponse.DeliveryGetResponse;
import com.sidenow.domain.boardType.delivery.board.dto.res.DeliveryResponse.DeliveryUpdateResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DeliveryService {
    DeliveryCreateResponse createDelivery(DeliveryCreateRequest req, MultipartFile image);
    AllDeliverys getDeliveryList(Integer page);
    DeliveryGetResponse getDelivery(Long id);
    DeliveryUpdateResponse updateDelivery(Long id, DeliveryUpdateRequest req, MultipartFile image);
    void deleteDelivery(Long id);
    String updateLikeOfDelivery(Long id);

}
