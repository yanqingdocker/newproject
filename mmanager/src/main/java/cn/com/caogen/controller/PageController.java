package cn.com.caogen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@Controller
public class PageController {
    /**
     * 访问index界面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 主页框架
     *
     * @return
     */
    @RequestMapping("/mainframe")
    public String mainframe() {
        return "mainframe";
    }

    /**
     * 访问登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 会员管理--会员列表
     *
     * @return
     */
    @RequestMapping("/member/member_list")
    public String member_list() {
        return "member/member_list";
    }

    /**
     * 内部开户
     * @return
     */
    @RequestMapping("/member/createuser")
    public String createuser(){
        return "member/createuser";
    }
    /**
     *会员管理--账户管控
     *
     * @return
     */
    @RequestMapping("/member/account_list")
    public String account_list() {
        return "member/account_list";
    }
    /**
     *会员管理--会员支付宝
     *
     * @return
     */
    @RequestMapping("/member/alipay_list")
    public String alipay_list() {
        return "member/alipay_list";
    }
    /**
     *会员管理--银行卡绑定
     *
     * @return
     */
    @RequestMapping("/member/bankcard_list")
    public String bankcard_list() {
        return "member/bankcard_list";
    }

    /**
     *会员管理--账户交易记录
     *
     * @return
     */
    @RequestMapping("/member/opera_log")
    public String opera_log() {
        return "member/opera_log";
    }
    /**
     *消息通知--会员认证审批
     *
     * @return
     */
    @RequestMapping("/msgNotify/member_certificate")
    public String member_certificate() {
        return "msgNotify/member_certificate";
    }
    /**
     *消息通知--转账申请通知
     *
     * @return
     */
    @RequestMapping("/msgNotify/transfer_apply")
    public String transfer_apply() {
        return "msgNotify/transfer_apply";
    }
    /**
     *消息通知--提现申请通知
     *
     * @return
     */
    @RequestMapping("/msgNotify/cash_apply")
    public String cash_apply() {
        return "msgNotify/cash_apply";
    }
    /**
     *消息通知--充值通知
     *
     * @return
     */
    @RequestMapping("/msgNotify/recharge_record")
    public String recharge_record() {
        return "msgNotify/recharge_record";
    }

    /**
     *汇率修改
     *
     * @return
     */
    @RequestMapping("/exchangeRate/rate_change")
    public String rate_change() {
        return "exchangeRate/rate_change";
    }
    /**
     *汇率查询
     *
     * @return
     */
    @RequestMapping("/exchangeRate/rate")
    public String rate() {
        return "exchangeRate/rate";
    }
    /**
     *资金库--平台资金库
     *
     * @return
     */
    @RequestMapping("/capitalbank/capital_bank")
    public String capital_bank() {
        return "capitalbank/capital_bank";
    }
    /**
     *资金库--资金库初始化
     *
     * @return
     */
    @RequestMapping("/capitalbank/cash_initialization")
    public String cash_initialization() {
        return "capitalbank/cash_initialization";
    }
    /**
     *资金库--现金资金库
     *
     * @return
     */
    @RequestMapping("/capitalbank/cash_bank")
    public String cash_bank() {
        return "capitalbank/cash_bank";
    }
    /**
     *现金管理--现金存入
     *
     * @return
     */
    @RequestMapping("/cashmanage/cash_in")
    public String cash_in() {
        return "cashmanage/cash_in";
    }
    /**
     *现金管理--现金转出
     *
     * @return
     */
    @RequestMapping("/cashmanage/cash_out")
    public String cash_out() {
        return "cashmanage/cash_out";
    }
    /**
     *现金管理--现金转出
     *
     * @return
     */
    @RequestMapping("/cashmanage/cash_change")
    public String cash_change() {
        return "cashmanage/cash_change";
    }
    /**
     *现金管理--现金交易记录
     *
     * @return
     */
    @RequestMapping("/cashmanage/cashtrade_list")
    public String cashtrade_list() {
        return "cashmanage/cashtrade_list";
    }
    /**
     *后台管理员--管理员角色
     *
     * @return
     */
    @RequestMapping("/manage/manage_role")
    public String manage_role() {
        return "manage/manage_role";
    }
    /**
     *后台管理员--管理员列表
     *
     * @return
     */
    @RequestMapping("/manage/manage_list")
    public String manage_list() {
        return "manage/manage_list";
    }
    /**
     *后台管理员--修改密码
     *
     * @return
     */
    @RequestMapping("/manage/changepsw")
    public String changepsw() {
        return "manage/changepsw";
    }
    /**
     *后台管理员--修改信息
     *
     * @return
     */
    @RequestMapping("/manage/changeinfo")
    public String changeinfo() {
        return "manage/changeinfo";
    }
    /**
     *后台管理员--管理员权限
     *
     * @return
     */
    @RequestMapping("/manage/manage_power")
    public String manage_power() {
        return "manage/manage_power";
    }
    /**
     *后台管理员--添加角色
     *
     * @return
     */
    @RequestMapping("/manage/add_role")
    public String add_role() {
        return "manage/add_role";
    }
    /**
     *日志管理
     *
     * @return
     */
    @RequestMapping("/oplog/log")
    public String log() {
        return "oplog/log";
    }

    /**
     *收入分析--月分析
     *
     * @return
     */
    @RequestMapping("/incomeAnalysis/month")
    public String month() {
        return "incomeAnalysis/month";
    }

    /**
     *收入分析--周分析
     *
     * @return
     */
    @RequestMapping("/incomeAnalysis/week")
    public String week() {
        return "incomeAnalysis/week";
    }
    /**
     *收入分析--季度分析
     *
     * @return
     */
    @RequestMapping("/incomeAnalysis/quarter")
    public String quarter() {
        return "incomeAnalysis/quarter";
    }

    /**
     *支出分析--月分析
     *
     * @return
     */
    @RequestMapping("/spentAnalysis/month")
    public String spentmonth() {
        return "spentAnalysis/month";
    }

    /**
     *支出分析--周分析
     *
     * @return
     */
    @RequestMapping("/spentAnalysis/week")
    public String spentweek() {
        return "spentAnalysis/week";
    }

    /**
     *支出分析--季度分析
     *
     * @return
     */
    @RequestMapping("/spentAnalysis/quarter")
    public String spentquarter() {
        return "spentAnalysis/quarter";
    }
    /**
     *任务处理--待处理
     *
     * @return
     */
    @RequestMapping("/tast/tast_nodeal")
    public String tast_nodeal() {
        return "tast/tast_nodeal";
    }
    /**
     *任务处理--处理中
     *
     * @return
     */
    @RequestMapping("/tast/tast_dealing")
    public String tast_dealing() {
        return "tast/tast_dealing";
    }
    /**
     *任务处理--已完成
     *
     * @return
     */
    @RequestMapping("/tast/tast_finish")
    public String tast_finish() {
        return "tast/tast_finish";
    }

    /**
     *网点管理--业务提成
     *
     * @return
     */
    @RequestMapping("/network/profit_list")
    public String profit_list() {
        return "network/profit_list";
    }
    /**
     *网点管理--网点列表
     *
     * @return
     */
    @RequestMapping("/network/network_list")
    public String network_list() {
        return "network/network_list";
    }

    /**
     *查看具体网点利润
     *
     * @return
     */
    @RequestMapping("/network/cash_bank")
    public String networkcash_bank() {
        return "network/cash_bank";
    }
    /**
     *网点管理--网点添加
     *
     * @return
     */
    @RequestMapping("/network/add_network")
    public String add_network() {
        return "network/add_network";
    }
    /**
     *借贷管理--担保人列表
     *
     * @return
     */
    @RequestMapping("/loanManage/guarantee_list")
    public String guarantee_list() {
        return "loanManage/guarantee_list";
    }
    /**
     *借贷管理--添加担保人
     *
     * @return
     */
    @RequestMapping("/loanManage/add_guarantee")
    public String add_guarantee() {
        return "loanManage/add_guarantee";
    }
    /**
     *借贷管理--借贷记录
     *
     * @return
     */
    @RequestMapping("/loanManage/loan_list")
    public String loan_list() {
        return "loanManage/loan_list";
    }
    /**
     *借贷管理--添加借贷
     *
     * @return
     */
    @RequestMapping("/loanManage/add_loan")
    public String add_loan() {
        return "loanManage/add_loan";
    }
    /**
     *现金柜台-数据恢复
     *
     * @return
     */
    @RequestMapping("/cashmanage/data_recovery")
    public String data_recovery() {
        return "cashmanage/data_recovery";
    }

    /**
     *资金库-利润分配列表
     *
     * @return
     */
    @RequestMapping("/capitalbank/profit_fplist")
    public String profit_fplist() {
        return "capitalbank/profit_fplist";
    }
    /**
     *资金库-新增利润分配
     *
     * @return
     */
    @RequestMapping("/capitalbank/add_profit_fp")
    public String add_profit_fp() {
        return "capitalbank/add_profit_fp";
    }
    /**
     *资金库-日常损耗列表
     *
     * @return
     */
    @RequestMapping("/capitalbank/add_dailyloss")
    public String add_dailyloss() {
        return "capitalbank/add_dailyloss";
    }
    /**
     *资金库-新增日常损耗
     *
     * @return
     */
    @RequestMapping("/capitalbank/daily_loss")
    public String daily_loss() {
        return "capitalbank/daily_loss";
    }

    /**
     *APP管理--安卓url管理
     *
     * @return
     */
    @RequestMapping("/appmanage/androidurl")
    public String androidurl() {
        return "appmanage/androidurl";
    }
    /**
     *APP管理--苹果url管理
     *
     * @return
     */
    @RequestMapping("/appmanage/iosurl")
    public String iosurl() {
        return "appmanage/iosurl";
    }
    /**
     *APP管理--公告管理
     *
     * @return
     */
    @RequestMapping("/appmanage/announcement")
    public String announcement() {
        return "appmanage/announcement";
    }






}
