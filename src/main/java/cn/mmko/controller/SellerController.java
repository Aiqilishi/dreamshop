package cn.mmko.controller;

import cn.mmko.dto.SellerCreateDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.seller.imp.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Resource
    private SellerService sellerService;
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Response< String> insertSeller(@RequestBody SellerCreateDTO sellerCreateDTO){
          sellerService.insertSeller(sellerCreateDTO);
          return Response.<String>builder()
                  .code(ResponseCode.SUCCESS.getCode())
                  .info(ResponseCode.SUCCESS.getInfo())
                  .build();
    }
//    @RequestMapping(value = "/query",method = RequestMethod.GET)
//    public Response<SellerLoginDTO> querySeller(@RequestBody SellerLoginDTO sellerLoginDTO){
//
//    }

}
