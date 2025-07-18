package cn.mmko.controller;

import cn.mmko.dto.product.*;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.product.IProductService;
import cn.mmko.vo.ProductInfoVO;
import cn.mmko.vo.ProductListVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private IProductService productService;

    /**
     * 首页分页查询商品
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public Response<PageInfo<ProductListVO>> queryProduct(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<ProductListVO> productListVOPageInfo = productService.queryProduct(pageNum, pageSize);
        return Response. <PageInfo<ProductListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productListVOPageInfo)
                .build();
    }

    /**
     *  商家查询商品
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */

    @RequestMapping(value = "/queryBySeller",method = RequestMethod.GET)
    public Response<PageInfo<ProductManageDTO>> queryProductBySellerId(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize,HttpServletRequest  request){
        Long sellerId = (Long) request.getAttribute("userId");
        PageInfo<ProductManageDTO> productDTOPageInfo = productService.queryProductBySellerId(pageNum, pageSize, sellerId);
        return Response. <PageInfo<ProductManageDTO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productDTOPageInfo)
                .build();
    }

    /**
     * 按商品id查询商品
     * @param productId
     * @return
     */
    @RequestMapping(value = "/query/{productId}",method = RequestMethod.GET)
    public Response<ProductInfoVO> queryProductById(@PathVariable Long productId){
        ProductInfoVO productInfoVO = productService.queryProductById(productId);
        return Response.<ProductInfoVO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productInfoVO)
                .build();
    }
    /**
     * 按关键字查询商品
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
@RequestMapping(value = "/queryBySearch",method = RequestMethod.GET)
    public Response<PageInfo<ProductListVO>> queryProductBySearch(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize,String  keyword) {
        PageInfo<ProductListVO> productListVOPageInfo = productService.queryProductBySearch(pageNum, pageSize, keyword);
        return Response. <PageInfo<ProductListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productListVOPageInfo)
                .build();
    }

    /**
     *  按分类查询商品
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/queryByCategoryId",method = RequestMethod.GET)
    public Response<PageInfo<ProductListVO>> queryProductByCategoryId(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize,@RequestParam Long  categoryId) {
        PageInfo<ProductListVO> productListVOPageInfo = productService.queryProductByCategoryId(pageNum, pageSize, categoryId);
        return Response. <PageInfo<ProductListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productListVOPageInfo)
                .build();
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Response<String> insertProduct(@RequestBody ProductCreateDTO productCreateDTO,HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        productCreateDTO.setUserId(userId);
        productService.insertProduct(productCreateDTO);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();

    }
    @RequestMapping(value = "/updateView/{productId}",method = RequestMethod.POST)
    public Response<String> updateProductViewCount(@PathVariable Long productId){
        productService.updateProductviewCount(productId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
    @RequestMapping(value = "/updateStock/{productId}",method = RequestMethod.POST)
    public Response<String> updateProductStock(@PathVariable Long productId){
        productService.updateProductStock(productId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
    @RequestMapping(value = "/delete/{productId}",method = RequestMethod.DELETE)
    public Response<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Response<String> updateProduct(@RequestBody ProductUpdateDTO productUpdateDTO){
        productService.updateProduct(productUpdateDTO);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
     @RequestMapping(value = "/queryImages/{productId}",method = RequestMethod.GET)
     public Response<List<ProductImagesDTO>> queryProductImages(@PathVariable Long productId){
        List<ProductImagesDTO> productImagesDTO =productService.queryProductImages(productId);
        return Response.<List<ProductImagesDTO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productImagesDTO)
                .build();
    }
    @RequestMapping(value = "/insertImages/{productId}")
    public Response<String> insertProductImages(@RequestBody ProductImagesDTO productImagesDTO,@PathVariable Long productId){
        productService.insertProductImages(productImagesDTO,productId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
    @RequestMapping(value = "/deleteImages/{productId}/{productImageId}",method = RequestMethod.DELETE)
    public Response<String> deleteProductImages(@PathVariable Long productId,@PathVariable Long productImageId){
        productService.deleteProductImages(productId,productImageId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
    @RequestMapping(value = "/updateImages/{productId}",method = RequestMethod.POST)
    public Response<String> updateProductImages(@RequestBody ProductImagesDTO productImagesDTO,@PathVariable Long productId){
        productService.updateProductImages(productImagesDTO,productId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
@RequestMapping(value = "/uplodeImages",method = RequestMethod.POST)
    public Response<String> uplodeProductImages(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws  IOException {
        if(file.isEmpty()) return Response.<String>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info(ResponseCode.UN_ERROR.getInfo())
                .build();
        String originalFilename = file.getOriginalFilename();
        String shuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID()+ shuffix;
        String uploadDir = "D:/images/";
        File fileDir = new File(uploadDir);
        if(!fileDir.exists()) fileDir.mkdirs();
        File dest = new File(fileDir,fileName);
        file.transferTo(dest);
        String imageUrl = "http://localhost:8091/images/" + fileName;
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(imageUrl)
                .build();
    }
}
