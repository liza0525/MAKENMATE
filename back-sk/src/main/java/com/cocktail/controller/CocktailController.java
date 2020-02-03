package com.cocktail.controller;

import com.cocktail.model.BasicResponse;
import com.cocktail.model.Cocktail;
import com.cocktail.model.Material;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cocktail.dao.CocktailDao;
import com.cocktail.dao.MaterialDao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aj.org.objectweb.asm.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
                @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
                @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
                @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RestController
public class CocktailController {
        @Autowired
        CocktailDao cocktailDao;

        @GetMapping("/cocktail/list")
        @ApiOperation(value = "칵테일 리스트")
        public Object list(
                // @PageableDefault(size = 20, sort = { "cid" }, direction = Direction.ASC)
        Pageable pageable, @RequestParam(required = true) final String filtered,
        HttpServletRequest req) {
        	System.out.println(req.getHeader("authorization"));
        if (filtered.equals("all")) {
                return cocktailDao.findAll(pageable);
        } else {
                String newFiltered = "%" + filtered + "%";
                System.out.println(cocktailDao.findByMaterialLike(newFiltered, pageable));
                return cocktailDao.findByMaterialLike(newFiltered, pageable);
        }

}

        @GetMapping("/cocktail/detail/{cid}")
        @ApiOperation(value = "칵테일 디테일")
        public Object detail(@PathVariable final int cid) {
                Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "success";
                result.object = cocktail;
                return new ResponseEntity<>(result, HttpStatus.OK);
        }

}