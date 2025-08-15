package cn.mmko.controller;

import cn.mmko.dto.CategoryCreateDTO;
import cn.mmko.dto.CategoryUpdateDTO;
import cn.mmko.dto.CategoryDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.category.ICategoryService;
import cn.mmko.service.category.imp.CategoryService;
import cn.mmko.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private ICategoryService categoryService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Response<String> insertCategory(@RequestParam String categoryName,HttpServletRequest  request) {
        Long sellerId = null;
        List<String> roles = (List<String>) request.getAttribute("role");
        if (!roles.contains("ADMIN")){
            sellerId = (Long) request.getAttribute("sellerId");
        }
        categoryService.insertCategory(categoryName, sellerId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
   @RequestMapping(value = "/query/publicCategory",method = RequestMethod.GET)
    public Response<List<CategoryVO>> queryPuCategory(){
         List<CategoryVO> categoryVO = categoryService.queryPuCategory();
        return Response.<List<CategoryVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(categoryVO)
                .build();
    }

    @RequestMapping(value = "/query/sellerCategory",method = RequestMethod.GET)
    public Response<List<CategoryVO>> querySeCategory(HttpServletRequest request){
        Long sellerId = (Long) request.getAttribute("sellerId");
        List<CategoryVO> categoryVO = categoryService.queryCategorySeCategory(sellerId);
        return Response.<List<CategoryVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(categoryVO)
                .build();
    }

@RequestMapping(value = "/query/pusellerCategory",method = RequestMethod.GET)
    public Response<List<CategoryVO>> queryPuSellerCategory(HttpServletRequest  request){
        Long sellerId = (Long) request.getAttribute("sellerId");
        List<CategoryVO> categoryVOS = categoryService.queryPuSellerCategory(sellerId);
        return Response.<List<CategoryVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(categoryVOS)
                .build();
    }
    
    /**
     * 更新分类
     * @param categoryUpdateDTO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response<String> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        categoryService.updateCategory(categoryUpdateDTO);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
    
    /**
     * 删除分类
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/delete/{categoryId}", method = RequestMethod.DELETE)
    public Response<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }
}
