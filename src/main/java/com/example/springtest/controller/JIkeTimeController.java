package com.example.springtest.controller;

import com.example.springtest.jksj.column.ColumnCollectorResponse;
import com.example.springtest.jksj.dto.ColumnDto;
import com.example.springtest.jksj.util.CollectorUtil;
import com.example.springtest.jksj.util.ExcelUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/jksj")
public class JIkeTimeController {
    // 创建webclient
//    public static void main(String[] args) throws IOException {
//        //需要采集的专栏id
//        long cid = 306L;
//
//
//
//
//    }

    /**
     * 导出极客时间专栏报表
     *
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void exportJksjData(@RequestParam Long cid, HttpServletRequest request, HttpServletResponse response) {
        ColumnDto columnDto = CollectorUtil.articleList(cid);
        ColumnCollectorResponse columnCollectorResponse = columnDto.getColumnCollectorResponse();
        String[] tableHeader = {"编号",  "文章id", "文章标题",  "文章链接"}; //excel文件名
      //sheet名
        String sheetName = columnCollectorResponse.getColumn_title();
        String fileName = "极客时间-"+sheetName + new SimpleDateFormat("yyyyMMdd").format(new Date())+ System.currentTimeMillis() + ".xls";

        String[][] content=new String[ columnCollectorResponse.getArticleList().size()][tableHeader.length];;
        for (int i = 0; i < columnCollectorResponse.getArticleList().size(); i++) {
            ColumnCollectorResponse.Article obj = columnCollectorResponse.getArticleList().get(i);
            content[i][0] = (i + 1) + "";
            content[i][1] = obj.getId().toString();
            content[i][2] = obj.getArticle_title();
            content[i][3] = obj.getArticle_url();
        }

//创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, tableHeader, content, null);

//响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void catchjksj() throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
        // 取消 JS 支持
//        webClient.getOptions().setJavaScriptEnabled(false);
//        // 取消 CSS 支持
//        webClient.getOptions().setCssEnabled(false);
        // 获取指定网页实体
        webClient.getOptions().setJavaScriptEnabled(true);//运行js脚本执行
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置支持AJAX
        webClient.getOptions().setCssEnabled(true);//忽略css
        webClient.getOptions().setUseInsecureSSL(true);//ssl安全访问
        webClient.getOptions().setThrowExceptionOnScriptError(false);  //解析js出错时不抛异常
        webClient.getOptions().setTimeout(10000);

        HtmlPage page = (HtmlPage) webClient.getPage("https://time.geekbang.org/column/intro/306");//

        System.out.println("页面title文本:" + page.getTitleText());
        System.out.println("-------------------执行js脚本之前");
        System.out.println(page.asXml());//页面文档结构字符串

        webClient.waitForBackgroundJavaScript(5000);   //等待js脚本执行完成
        System.out.println("-------------------执行js脚本后");
        System.out.println(page.asXml());//页面文档结构字符串
        List<HtmlElement> a = page.getByXPath("//a[@class='_1L7v-KHJ_0']");


        // “点击” 搜索
        HtmlPage page2 = a.get(0).click();
        // 选择元素
        List<HtmlElement> spanList = page2.getByXPath("//div[@class='_3c2pu66u_0 _2ErUWiOJ_0']/a");
        System.out.println(spanList.size());
//        for(int i=0;i<spanList.size();i++) {
//            // 输出新页面的文本
//            System.out.println(i+1+"、"+spanList.get(i).asText()+"url"+spanList.get(i).getAttribute("href"));
//        }

    }

}
