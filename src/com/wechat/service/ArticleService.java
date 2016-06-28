package com.wechat.service;

import java.math.BigInteger;
import java.util.Map;

import com.wechat.entity.Article;
import com.wechat.util.PageQueryUtil;

/**
 * 文章接口
 * @Description:
 * @author zhur
 * @date 2016年6月6日 下午4:45:25
 */
public interface ArticleService extends BaseService {
	
	//分页查询
	public Map<String, Object> findArticlesByPage(Article article, PageQueryUtil page);
	
	//删除
	public Integer deleteArticle(BigInteger id);
	
}
