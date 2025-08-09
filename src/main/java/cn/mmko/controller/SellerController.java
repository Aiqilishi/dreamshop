package cn.mmko.controller;

import cn.mmko.dto.SellerCreateDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.seller.imp.SellerService;
import cn.mmko.service.upload.imp.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Resource
    private SellerService sellerService;
    @Resource
    private FileUploadService fileUploadService;
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Response< String> insertSeller(@RequestBody SellerCreateDTO sellerCreateDTO, HttpServletRequest  request){
        Long userId = (Long) request.getAttribute("userId");
          sellerService.insertSeller(sellerCreateDTO,userId);
          return Response.<String>builder()
                  .code(ResponseCode.SUCCESS.getCode())
                  .info(ResponseCode.SUCCESS.getInfo())
                  .build();
    }
    @RequestMapping(value = "/uploadLogo",method = RequestMethod.POST)
    public Response<String> uploadSellerLogo(@RequestParam ("file") MultipartFile file, HttpServletRequest request) throws Exception{
        Long sellerId = (Long) request.getAttribute("sellerId");
        String logoUrl = fileUploadService.uploadSellerLogo(file,sellerId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(logoUrl)
                .build();
    }
//    @RequestMapping(value = "/query",method = RequestMethod.GET)
//    public Response<SellerLoginDTO> querySeller(@RequestBody SellerLoginDTO sellerLoginDTO){
//
//    }

}
