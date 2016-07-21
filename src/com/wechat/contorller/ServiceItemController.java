package com.wechat.contorller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.Case;
import com.wechat.entity.ServiceItem;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.CaseService;
import com.wechat.service.ServiceItemService;
import com.wechat.service.UserService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.MyDateUtil;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/service")
@Module("Services")
public class ServiceItemController extends BaseController {
    
	private static final Log4jLogger log = Log4jLogger
			.getLogger(ServiceItemController.class);
	
	@Resource
	private ServiceItemService serviceItemService;

	@Resource
	private UserService userService;

	@Resource
	private CaseService caseService;
	
	@RequestMapping(value="/xheditorFileUpload" , method = RequestMethod.POST)
	@Prev(module="ServiceItemManager" ,oprator="all")
	public void xheditorFileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ServletOutputStream out = response.getOutputStream();
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");
        
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 设置编码
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile image = multipartRequest.getFile("filedata");
            String url = "images"+File.separator+MyDateUtil.dateToString(new Date(), "yyyyMMdd")+File.separator;
            //这里可以从项目中取得你项目根目录的地址
            //怎样 访问 应用目录外的资源文件
/*            String filePath = request.getSession().getServletContext().getRealPath("/")+"/upload/";*/
            //String filePath = "D:/opt"+"/pic/";
            String filePath=request.getSession().getServletContext().getRealPath("/");
            String newPath=filePath.substring(0, filePath.indexOf("\\wechat-manager"));
            //filePath.replace("wechat-manager", "ROOT");
            newPath+="/ROOT/upload/";
            Random r = new Random();
            if(image != null && !image.isEmpty()){
                InputStream xtins = image.getInputStream();
                String hj = new String(image.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
                hj = hj.split("\\.")[1];
                //这里用来生成文件名
                String fileName = MyDateUtil.dateToString(new Date(), "yyyyMMddHHmmss")+r.nextInt(1000)+"."+hj;
                saveInputStreamToFile(newPath, fileName, xtins);
                //这里是用来返回给xheditor的
/*                out.print("{'err':'','msg':'"+ (request.getContextPath() + "/upload/" +fileName).replace("\\", "/")+"'}");
*/               // out.print("{'err':'','msg':'"+ ("D:/opt" + "/pic/" +fileName).replace("\\", "/")+"'}");
                out.print("{'err':'','msg':'"+ ("../../upload/" +fileName).replace("\\", "/")+"'}");
                
            }
        }
	}
	/**
     * 保存文件流到指定路径
     * @param filePath
     * @param fileName
     * @param inputStream
     * @throws Exception
     */
    public void saveInputStreamToFile(String filePath, String fileName,
            InputStream inputStream) throws Exception {
        OutputStream os = null;
        try {
            File forder = new File(filePath);
            if (!forder.exists()) {
                forder.mkdir();
            }
            File file = new File(filePath + fileName);
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
	
	@RequestMapping("/serviceItemList")
	@Prev(module="serviceItemList",oprator="all")
	public ModelAndView serviceItemList(ServiceItem item,PageQueryUtil page) throws Exception {
		return backView("serviceItem/serviceItemList", serviceItemService.findServiceItemByPage(item, page));
	}

	
	@RequestMapping("/updateServiceItem")
	@Prev(module = "ServiceItemManager", oprator = "update")
	public ModelAndView updateServiceItem(ServiceItem item,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (item.getServiceName()!= null) {
				item.setServiceName(new String(item.getServiceName().getBytes("iso-8859-1"),"UTF-8"));
			}
			return new ModelAndView("serviceItem/addServiceItemInfo", "item",
					serviceItemService.get(ServiceItem.class, id)).addObject(
					"itemInfo", item).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 通过id判断是否修改或新增
	 * @param honor
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addServiceItemCommit", method = RequestMethod.POST)
	@Prev(module = "ServiceItemManager", oprator = "add")
	@ResponseBody
	public ModelMap addServiceItemCommit(ServiceItem item, String createTime)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(item);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(item);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				item.setCreateUser(user.getId());
				item.setCreateTime(MyDateUtil.stringToTimestamp(createTime));
				item.setIsDel(Constrants.DATA_NOT_DEL);
				if (item.getId() == null) {
					serviceItemService.save(item);
				} else {
					serviceItemService.update(item);
				}
				map.put(Constrants.MESSAGE_TIP, "操作成功！");
			}
			map.put(Constrants.MESSAGE_TIP_FLAGS, flag);
			return map;
		} catch (Exception e) {
			log.error("addEmployeeInfoCommit error", e);
			throw e;
		}
	}
	
	/**
	 * 跳转到新增页
	 * @return
	 */
	@RequestMapping("/addServiceItem")
	@Prev(module = "ServiceItemManager", oprator = "add")
	public String addArticle() {
		return "serviceItem/addServiceItemInfo";
	}
	
	/**
	 * 删除记录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteServiceItem",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="ServiceItemManager",oprator="delete")
	public ModelMap deleteServiceItemInfo(Integer id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && serviceItemService.deleteServiceItem(id) == 1) {
				map.put(Constrants.MESSAGE_TIP, "删除成功");
				map.put(Constrants.MESSAGE_TIP_FLAGS, true);
			} else {
				map.put(Constrants.MESSAGE_TIP, "删除失败");
				map.put(Constrants.MESSAGE_TIP_FLAGS, false);
			}
			return map;
		} catch (Exception e) {
			log.error("deleteArticle error", e);
			throw e;
		}
	}
	
	@RequestMapping(value="/bindCases/{serviceId}")
	@Prev(module = "ServiceItemManager", oprator = "bind")
	public ModelAndView bindCases(@PathVariable int serviceId,PageQueryUtil page){
		
		//未绑定案例
		List<Case> notBindCase=serviceItemService.queryNotBindCaseByServiceId(serviceId);
		//已经绑定的案例
		List<Case> bindCase=serviceItemService.queryCaseByServiceId(serviceId);
		//return "serviceItem/bindServiceCases";
		return new ModelAndView("serviceItem/bindServiceCases","notBindCase",notBindCase).addObject("bindCase", bindCase)
				.addObject("serviceId", serviceId).addObject("pageQueryUtil", page);
	}
	
	//@RequestBody String[] arrays,@RequestParam("arrays[]") List<String> ar
	@RequestMapping(value="/bindServiceCaseCommit/{serviceId}")
	@Prev(module = "ServiceItemManager", oprator = "bind")
	@ResponseBody
	public ModelMap bindServiceCaseCommit(@PathVariable int serviceId,@RequestParam("arrays[]") String[] arr){
		Map map=new HashMap();
		map.put("serviceId", serviceId);
		map.put("arrays", arr);
		boolean result=caseService.updateBindCaseByServiceId(map);
		ModelMap m=new ModelMap();
		m.put(Constrants.MESSAGE_TIP, "操作成功！");
		return m;
	}
	
}
