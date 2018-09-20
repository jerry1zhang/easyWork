package com.zking.controller.info;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zking.controller.base.BaseController;
import com.zking.controller.base.BasePageController;
import com.zking.dao.ProductMapper;
import com.zking.pojo.Producer;
import com.zking.pojo.Product;
import com.zking.service.BaseService;
import com.zking.service.impl.ProductServiceImpl;
import com.zking.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * InfoPageController
 *
 * @anthor zhanghy9
 * @date 2018-04-17 11:03
 */
@Controller
@RequestMapping("/info")
public class InfoController extends BasePageController{

    @Resource
    BaseService<Product> product;
    @Resource
    BaseService<Producer> producer;

    /**
     * 页面控制
     * @param model
     * @return
     */
    @Override
    @RequestMapping("/product")
    public String getPage(Model model){
        return "/info/product/list";
    }

    /**
     * productList
     * @param page 表格页数
     * @param rows 每页行数
     * @param selectType 搜索类别
     * @param selectValue 搜索值
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/productInfo/{page}/{rows}/{selectType}/{selectValue}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object listBySelect(@PathVariable int page,
                                       @PathVariable int rows,
                                       @PathVariable String selectType,
                                       @PathVariable String selectValue,
                                       Model model){
        //计算分页
        int start = (page-1)*rows;
        PageData sendData = new PageData();
        sendData.put("start",start);
        sendData.put("rows",rows);
        sendData.put("selectType",selectType);
        sendData.put("selectValue","%"+selectValue+"%");
        PageData resultData = product.getPartPageList(sendData);
        List<Product> products = (List<Product>)resultData.get("products");
        return new Gson().toJson(products);
    }
    @Override
    @RequestMapping(value="/productInfo/{page}/{rows}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object list(@PathVariable int page,
                              @PathVariable int rows,
                              Model model){
        //计算分页
        int start = (page-1)*rows;
        PageData sendData = new PageData();
        sendData.put("start",start);
        sendData.put("rows",rows);
        PageData resultData = product.getAllPageList(sendData);
        List<Product> products = (List<Product>)resultData.get("products");
        logger.info("products="+products);
        return new Gson().toJson(products);
    }

    @RequestMapping(value="/productInfo" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object list(Model model){
        //计算分页
        PageData sendData = new PageData();
        sendData.put("start",0);
        sendData.put("rows",0);
        PageData resultData = product.getAllPageList(sendData);
        List<Product> products = (List<Product>)resultData.get("products");
        logger.info("products="+products);
        return new Gson().toJson(products);
    }
    /**
     * 分页
     * @param rows
     * @param selectType
     * @param selectValue
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/productInfo/page/{rows}/{selectType}/{selectValue}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object pageBySelect(@PathVariable int rows,
                              @PathVariable String selectType,
                              @PathVariable String selectValue,
                              Model model){
        PageData sendData = new PageData();
        sendData.put("selectType",selectType);
        sendData.put("selectValue","%"+selectValue+"%");
        int sum = product.listSum(sendData);
        JsonObject result = new JsonObject();
        result.addProperty("listSum",sum);
        return new Gson().toJson(result);
    }

    @Override
    @RequestMapping(value="/productInfo/page/{rows}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object page(@PathVariable int rows,
                              Model model){
        PageData sendData = new PageData();
        sendData.put("selectType",null);
        sendData.put("selectValue",null);
        int sum = product.listSum(sendData);
        JsonObject result = new JsonObject();
        result.addProperty("listSum",sum);
        return new Gson().toJson(result);
    }

    @Override
    @RequestMapping(value="/productInfo/add/{newProduct}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object add(@PathVariable String newProduct,
                             Model model){
        Product item = new Gson().fromJson(newProduct,Product.class);
        int sum = 0;
        if(product.add(item)){
            sum+=1;
        }
        JsonObject result = new JsonObject();
        result.addProperty("addSum",sum);
        return new Gson().toJson(result);
    }

    @Override
    @RequestMapping(value="/productInfo/edit/{changeProduct}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object edit(@PathVariable String changeProduct,
                             Model model){
        Product item = new Gson().fromJson(changeProduct,Product.class);
        int sum = 0;
        if(product.update(item)){
            sum+=1;
        }
        JsonObject result = new JsonObject();
        result.addProperty("changeSum",sum);
        return new Gson().toJson(result);
    }

    @Override
    @RequestMapping(value="/productInfo/del/{productIds}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object del(@PathVariable String productIds,
                             Model model){
        JsonObject result = new JsonObject();
        List<Product> pids = new Gson().fromJson(productIds,new TypeToken<List<Product>>(){}.getType());
        int sum = 0;
        for (Product productObject : pids){
            if(product.del(productObject)){
                sum+=1;
            }

        }
        result.addProperty("delSum",sum);
        return new Gson().toJson(result);
    }

}
