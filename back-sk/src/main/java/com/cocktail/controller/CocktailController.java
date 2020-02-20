package com.cocktail.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.dao.CocktailDao;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.Cocktail;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
                @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
                @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
                @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RestController
@RequestMapping(value = "/backend")
public class CocktailController {
        @Autowired
        CocktailDao cocktailDao;

        @GetMapping("/cocktail/list")
        @ApiOperation(value = "칵테일 리스트")
        public Object list(
                        // @PageableDefault(size = 20, sort = { "cid" }, direction = Direction.ASC)
                        final Pageable pageable, @RequestParam(required = true) final String filtered,
                        @RequestParam(required = true) final String searchedFiltered) {
                if (filtered.equals("all")) {
                        if (searchedFiltered.equals("h")) {
                                return cocktailDao.findAll(pageable);
                        } else {
                                final String newSearchFiltered = "%" + searchedFiltered + "%";
                                return cocktailDao.findByCnameLike(newSearchFiltered, pageable);
                        }
                } else {
                        if (searchedFiltered.equals("h")) {
                                final String newFiltered = "%" + filtered + "%";
                                System.out.println(cocktailDao.findByMaterialLike(newFiltered, pageable));
                                return cocktailDao.findByMaterialLike(newFiltered, pageable);
                        } else {

                                final String newSearchFiltered = "%" + searchedFiltered + "%";
                                final String newFiltered = "%" + filtered + "%";
                                List<Cocktail> sList = cocktailDao.findByCnameLike(newSearchFiltered);
                                List<Cocktail> mList = cocktailDao.findByMaterialLike(newFiltered);
                                List<Cocktail> ret = new ArrayList<>();
                                for (int i = 0; i < sList.size(); i++) {
                                        for (int j = 0; j < mList.size(); j++) {
                                                if (sList.get(i).equals(mList.get(j))) {
                                                        ret.add(sList.get(i));
                                                        break;
                                                }
                                        }
                                }
                                Page<Cocktail> result = new PageImpl<>(ret, pageable, ret.size());
                                return result;
                        }
                }

        }

        @GetMapping("/cocktail/detail/{cid}")
        @ApiOperation(value = "칵테일 디테일")
        public Object detail(@PathVariable final int cid) {
                final Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "success";
                result.object = cocktail;
                return new ResponseEntity<>(result, HttpStatus.OK);
        }

        @GetMapping("/cocktail/name")
        @ApiOperation(value = "칵테일 이름")
        public Object cocktailName() {
                final List<Cocktail> cocktailArray = cocktailDao.findAll();
                List<String> cocktailNameArray = new ArrayList<>();
                for (int i = 0; i < cocktailArray.size(); i++) {
                        cocktailNameArray.add(cocktailArray.get(i).getCname());
                }
                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "success";
                result.object = cocktailNameArray;
                return new ResponseEntity<>(result, HttpStatus.OK);
        }

}