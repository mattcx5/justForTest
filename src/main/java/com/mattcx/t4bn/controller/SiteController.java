package com.mattcx.t4bn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mattcx.t4bn.dao.SiteDao;
import com.mattcx.t4bn.model.Site;

@RestController
@RequestMapping("/site")
public class SiteController {

    @Autowired
    private SiteDao siteDao;
	
	/** 
     * 頁面-新增站點頁
     * --
     *  
     * */
    @RequestMapping("/add")
    public ModelAndView siteAddPage(){
    	
    	System.out.println("run: siteAddPage");
    	
        ModelAndView modelAndView = new ModelAndView("/site_new");
        return modelAndView;
    }		
	
    /** 
     * 執行-新增-站點 
     * --
     * 
     * */
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    public ModelAndView doAdd(HttpServletRequest req, HttpServletResponse resp) {
        
    	System.out.println("run: doAdd");
    	
    	String siteName = req.getParameter("siteName");
    	System.out.println("doAdd: siteName>>>"+siteName);
    	
    	Site site = new Site();
    	site.setSiteName(siteName);
    	//site.setCrtDatetime(new Timestamp(System.currentTimeMillis()));
    	//site.setUpdDatetime(new Timestamp(System.currentTimeMillis()));
    	
    	try {
    		siteDao.save(site);
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    	}    	
    	
        return new ModelAndView("redirect:/site");
    }  	
	
    /** 
     * 頁面-站點列表
     * --
     *  
     * */
    @RequestMapping("")
    public ModelAndView siteListPage(){
              
    	System.out.println("run: siteListPage");
    	
    	List<Site> siteList = (List<Site>)siteDao.findAll();
        
        ModelAndView modelAndView = new ModelAndView("/site_list");
        modelAndView.addObject("siteList", siteList);
        
        return modelAndView;
    }	
	    
    /** 
     * 頁面-編輯站點頁
     * --
     *  
     * */
    @RequestMapping("/edit/{siteId}")
    public ModelAndView siteUpdPage(@PathVariable("siteId") String siteId){
    	
    	System.out.println("run: siteUpdPage");
    	System.out.println("siteId>>>"+siteId);
        
    	Site site = siteDao.findOne(new Long(siteId));
    	
        ModelAndView modelAndView = new ModelAndView("/site_edit");
        modelAndView.addObject("siteId", siteId);
        modelAndView.addObject("site", site);
            
        return modelAndView;
    }	    
    
    /** 
     * 執行-編擊-站點 
     * --
     * 
     * */
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    public ModelAndView doEdit(HttpServletRequest req, HttpServletResponse resp) {
        
    	System.out.println("run: doEdit");
    	
    	String siteId = req.getParameter("siteId");
    	String siteName = req.getParameter("siteName");
    	System.out.println("doEdit: siteId>>>"+siteId);
    	System.out.println("doEdit: siteName>>>"+siteName);
    	
    	Site site = new Site();
    	site.setSiteId(new Long(siteId));
    	site.setSiteName(siteName);
    	//site.setUpdDatetime(new Timestamp(System.currentTimeMillis()));    	
    	siteDao.save(site);
    	
        return new ModelAndView("redirect:/site");
    } 
    
    /** 
     * 執行-刪除-站點 
     * --
     * 
     * */
    @RequestMapping("/doDel/{siteId}")
    public ModelAndView doDel(@PathVariable("siteId") String siteId) {
        
    	System.out.println("run: doDel");
    	System.out.println("doDel: siteId>>>"+siteId);
    	
    	siteDao.delete(new Long(siteId));
    	
        return new ModelAndView("redirect:/site");
    }       
    
}