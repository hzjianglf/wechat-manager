package com.wechat.contorller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.Article;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.ArticleService;
import com.wechat.service.UserService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.MyDateUtil;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/article")
@Module("Newss")
public class ArticleController extends BaseController {
    
	private static final Log4jLogger log = Log4jLogger
			.getLogger(ArticleController.class);
	
	@Resource
	private ArticleService articleService;

	@Resource
	private UserService userService;

	@RequestMapping(value="/xheditorFileUpload" , method = RequestMethod.POST)
	@Prev(module="ArticleManager" ,oprator="all")
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
	/**
	 * 文章信息列表页
	 * @param honor
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/articleList")
	@Prev(module="articleInfoList",oprator="all")
	public ModelAndView articleList(Article article,PageQueryUtil page) throws Exception {
		return backView("news/articleList", articleService.findArticlesByPage(article, page));
	}

	
	/**
	 * 修改
	 * @param honor
	 * @param page
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateArticle")
	@Prev(module = "ArticleManager", oprator = "update")
	public ModelAndView updateArticle(Article article,
			PageQueryUtil page, BigInteger id) throws Exception {
		try {
			if (article.getTitle() != null) {
				article.setTitle(new String(article.getTitle().getBytes("iso-8859-1"),"UTF-8"));
			}
			return new ModelAndView("news/addArticleInfo", "article",
					articleService.get(Article.class, id)).addObject(
					"news", article).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			log.error("updateArticle error", e);
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
	@RequestMapping(value = "/addArticleCommit", method = RequestMethod.POST)
	@Prev(module = "ArticleManager", oprator = "add")
	@ResponseBody
	public ModelMap addArticleCommit(Article article, String createTime)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(article);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(article);
				article.setCreateTime(MyDateUtil.stringToTimestamp(createTime));
				article.setIsDel(Constrants.DATA_NOT_DEL);
				if (article.getId() == null) {
					articleService.save(article);
				} else {
					articleService.update(article);
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
	@RequestMapping("/addArticles")
	@Prev(module = "ArticleManager", oprator = "add")
	public String addArticle() {
		return "news/addArticleInfo";
	}
	
	/**
	 * 删除记录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteArticle",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="ArticleManager",oprator="delete")
	public ModelMap deleteArticleInfo(BigInteger id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && articleService.deleteArticle(id) == 1) {
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
}
