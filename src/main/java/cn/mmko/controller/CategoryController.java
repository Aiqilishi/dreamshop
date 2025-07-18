package cn.mmko.controller;

import cn.mmko.dto.CategoryCreateDTO;
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
    public Response<String> insertCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        categoryService.insertCategory(categoryCreateDTO);
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

    @RequestMapping(value = "/query/sellerCategory/{sellerId}",method = RequestMethod.GET)
    public Response<List<CategoryVO>> querySeCategory(@PathVariable Long sellerId){
        List<CategoryVO> categoryVO = categoryService.queryCategorySeCategory(sellerId);
        return Response.<List<CategoryVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(categoryVO)
                .build();
    }

@RequestMapping(value = "/query/pusellerCategory",method = RequestMethod.GET)
    public Response<List<CategoryVO>> queryPuSellerCategory(HttpServletRequest  request){
        Long userId = (Long) request.getAttribute("userId");
        List<CategoryVO> categoryVOS = categoryService.queryPuSellerCategory(userId);
        return Response.<List<CategoryVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(categoryVOS)
                .build();
    }
}
