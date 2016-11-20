package com.jianong.service.wxd;

import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class HuaBanCrawl extends BreadthCrawler {

	public HuaBanCrawl(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
	}

	@Override
	public void visit(Page page, CrawlDatums crawlDatums) {
		if (page.matchUrl("http://huaban.com/pins/\\d+.*")) {
			Document doc = page.doc();
			System.out.println(doc.select("#baidu_image_holder > img").attr("scr"));
		}
	}

	public static void main(String[] args) throws Exception {
		HuaBanCrawl crawl = new HuaBanCrawl("huaban", true);
		crawl.addSeed("http://huaban.com/");
		crawl.addRegex("http://huaban.com/boards/\\d+.*");
		crawl.addRegex("http://huaban.com/pins/\\d+.*");
		crawl.setExecuteInterval(10000);
		crawl.setTopN(50);
		crawl.setThreads(1);
		crawl.start(3);
	}

}
