package cn.mmko.controller;

import cn.mmko.dto.ProductDetailDTO;
import cn.mmko.dto.ProductListDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.product.IProductService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private IProductService productService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public Response<PageInfo<ProductListDTO>> queryProduct(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ProductListDTO> productDTOPageInfo = productService.queryProduct(pageNum, pageSize);
        return Response. <PageInfo<ProductListDTO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productDTOPageInfo)
                .build();
    }
    @RequestMapping(value = "/query/{productId}",method = RequestMethod.GET)
    public Response<ProductDetailDTO> queryProductById(@PathVariable Long productId){
        ProductDetailDTO productDetailDTO = productService.queryProductById(productId);
        return Response.<ProductDetailDTO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productDetailDTO)
                .build();
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Response<String> insertProduct(){
        return null;
    }


}
