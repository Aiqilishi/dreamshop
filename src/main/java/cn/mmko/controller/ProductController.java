package cn.mmko.controller;

import cn.mmko.dto.product.*;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.product.IProductService;
import cn.mmko.service.upload.imp.FileUploadService;
import cn.mmko.vo.ProductImagesVO;
import cn.mmko.vo.ProductInfoVO;
import cn.mmko.vo.ProductListVO;
import cn.mmko.vo.ProductManageListVO;
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
    @Resource
    private FileUploadService fileUploadService;

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
    public Response<PageInfo<ProductManageListVO>> queryProductBySellerId(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, HttpServletRequest  request){
        Long sellerId = (Long) request.getAttribute("sellerId");
        List<String> roles = (List<String>) request.getAttribute("role");
        if (roles.contains("ROLE_ADMIN")){
            sellerId = null;
        }
        PageInfo<ProductManageListVO> productManageListVOPageInfo = productService.queryProductBySellerId(pageNum, pageSize, sellerId);
        return Response. <PageInfo<ProductManageListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productManageListVOPageInfo)
                .build();
    }
    @RequestMapping(value = "/queryBackgroundBySearch",method = RequestMethod.GET)
    public Response<PageInfo<ProductManageListVO>> queryProductBySearch( @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, String  keyword,HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("sellerId");
        List<String> roles = (List<String>) request.getAttribute("role");
        if (roles.contains("ROLE_ADMIN")){
            sellerId = null;
        }
        PageInfo<ProductManageListVO> productManageListVOPageInfo = productService.queryBackgroundBySearch(pageNum, pageSize, sellerId, keyword);
        return Response. <PageInfo<ProductManageListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productManageListVOPageInfo)
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
        Long sellerId = (Long) request.getAttribute("sellerId");
        productCreateDTO.setUserId(sellerId);
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
    /**
     * 删除商品
     * @param productIds
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Response<String> deleteProduct(@RequestBody List<Long> productIds){
        productService.deleteProduct(productIds);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }

    /**
     * 修改商品
     * @param productUpdateDTO
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Response<String> updateProduct(@RequestBody ProductUpdateDTO productUpdateDTO){
        productService.updateProduct(productUpdateDTO);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }

    /**
     * 修改商品状态
     * @param productId
     * @return
     */
    @RequestMapping(value = "/updateStatus/{productId}",method = RequestMethod.POST)
    public Response<String> updateProductStatus(@PathVariable Long productId){
        productService.updateProductStatus(productId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }


     @RequestMapping(value = "/queryImages/{productId}",method = RequestMethod.GET)
     public Response<List<ProductImagesVO>> queryProductImages(@PathVariable Long productId){
        List<ProductImagesVO> productImagesVOs =productService.queryProductImages(productId);
        return Response.<List<ProductImagesVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(productImagesVOs)
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
@RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    public Response<String> uploadProductImages(@RequestParam ("file") MultipartFile file, HttpServletRequest  request) throws Exception {
         Long sellerId = (Long) request.getAttribute("sellerId");
         String imageUrl = fileUploadService.uploadProductMainImage(file,sellerId);
         return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(imageUrl)
                .build();
    }
}
