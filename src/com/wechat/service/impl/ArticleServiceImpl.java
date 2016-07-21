package com.wechat.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.Article;
import com.wechat.service.ArticleService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;

@Service("articleService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ArticleServiceImpl extends BaseServiceImpl implements
		ArticleService {

	/*
	 * 文章分页列表带字段搜索
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#findHonorInfoByPage(com.oa.entity.Honor, com.oa.util.PageQueryUtil)
	 */
	public Map<String, Object> findArticlesByPage(Article article,
			PageQueryUtil page) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.id,a.title,a.content,a.pic,a.author,a.create_time createTime,a.is_del isDel,a.news_type as newsType  from weixin_hot_article a ")
		.append(" where a.is_del=?");
		List<Object> list = new ArrayList<Object>();
		list.add(Constrants.DATA_NOT_DEL);
		if(!StringTools.isEmpty(article.getTitle())){
			sql.append(" and a.title like ?");
			list.add("%" + article.getTitle().trim() + "%");
		}

		sql.append(" order by a.create_time desc");
		//当子查询中含有from字句时请大写FROM，不然会出错
		return backPageSql(sql.toString(), Article.class, page, list.toArray());
	}

	/*
	 * 根据id删除单条记录
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#deleteHonorInfo(java.lang.Long)
	 */
	@Override
	public Integer deleteArticle(BigInteger id) {
		String hql="update Article set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL,id);
	}
	
}
