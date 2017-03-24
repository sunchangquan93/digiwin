package digiwin.smartdepot.core.xml;

import android.content.Context;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import digiwin.library.utils.LogUtils;
import digiwin.smartdepot.module.bean.purchase.BadReasonBean;

/**
 * @author 唐孟宇
 * @des 收获检验 提交不良原因请求报文
 */
@XStreamAlias("soapenv:Envelope")
public class CreatePurchaseCheckReq {

    private Context context;

    public CreatePurchaseCheckReq() {

    }



    @XStreamOmitField
    private String reqType;

    /**
     * @param reqType   节点名
     * @param userName  用户名
     * @param plant     营用中心
     * @param map       parameter中报文
     * @param deviceId  设备号
     * @param appmodule 模组名
     * @param timestamp 设备号+模组名+时间
     */
    public CreatePurchaseCheckReq(String userName, String plant, Map<String, String> map, String deviceId, String appmodule, String reqType, String timestamp) {
        try {
            if(map==null){
                map=new HashMap<String,String>();
            }
            // 遍历Map的方法
            init(userName, plant, deviceId, appmodule,reqType, timestamp);
            Set<Entry<String, String>> sets = map.entrySet();
            for (Entry<String, String> entry : sets) {
                Object key = entry.getKey();
                Object val = entry.getValue();
                if (null == val) {
                    val = "";
                }
                Field field1 = new Field(key.toString(), val.toString());
                B_Body.Tip.TipRequest.Request.RequestContent.A_Parameter.Record.Field.add(field1);
            }
        } catch (Exception e) {
            LogUtils.e("CreateParaXmlReq", "组织报文异常");
        }
    }

    /**
     * @param reqType   节点名
     * @param userName  用户名
     * @param plant     营用中心
     * @param maps       RecordSet中报文循环recordset
     * @param deviceId  设备号
     * @param appmodule 模组名s
     * @param timestamp 设备号+模组名+时间
     */
    public CreatePurchaseCheckReq(String userName, String plant, List<Map<String, String>> maps, String deviceId, String appmodule, String reqType, String timestamp) {
        try {
            if(maps==null){
                maps=new ArrayList<>();
            }
            // 遍历Map的方法
            init(userName, plant, deviceId, appmodule,reqType, timestamp);
            for (int i = 0; i < maps.size(); i++) {
                RecordSet recordset1 = new RecordSet(String.valueOf(i + 1));
                Set<Entry<String, String>> sets = maps.get(i).entrySet();
                for (Entry<String, String> entry : sets) {
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    if (null == val) {
                        val = "";
                    }
                    Field field1 = new Field(key.toString(), val.toString());
                    recordset1.A_Master.Record.Field.add(field1);
                }
                B_Body.Tip.TipRequest.Request.RequestContent.B_Document.RecordSet.add(recordset1);
            }
        } catch (Exception e) {
            LogUtils.e("CreateParaXmlReq", "RecordSet中报文--组织报文异常");
        }
    }

    /**
     * @param reqType   节点名
     * @param userName  用户名
     * @param plant     营用中心
     * @param maps       RecordSet中报文循环recordset
     * @param deviceId  设备号
     * @param appmodule 模组名s
     * @param timestamp 设备号+模组名+时间
     */
    public CreatePurchaseCheckReq(String userName, String plant, List<Map<String, String>> maps, List<BadReasonBean> badReasonBeen,String deviceId, String appmodule, String reqType, String timestamp) {
        try {
            // 遍历Map的方法
            init(userName, plant, deviceId, appmodule,reqType, timestamp);
            for (int i = 0; i < maps.size(); i++) {
                RecordSet recordset1 = new RecordSet(String.valueOf(i + 1));
                Set<Entry<String, String>> sets = maps.get(i).entrySet();
                for (Entry<String, String> entry : sets) {
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    if (null == val) {
                        val = "";
                    }
                    Field field1 = new Field(key.toString(), val.toString());
                    recordset1.A_Master.Record.Field.add(field1);
                }
                Detail detail = new Detail();
                detail.name = "Detail";
                if (null != badReasonBeen) {
                    for (int j = 0; j < badReasonBeen.size(); j++) {
                        Record record = new Record();
                        Field field1 = new Field("defect_reason", badReasonBeen.get(j).getDefect_reason());
                        Field field2 = new Field("defect_reason_name", badReasonBeen.get(j).getDefect_reason_name());
                        Field field3 = new Field("defect_reason_qty", badReasonBeen.get(j).getDefect_qty());
                        record.Field.add(field1);
                        record.Field.add(field2);
                        record.Field.add(field3);
                        detail.Record.add(record);
                    }
                }
                recordset1.B_Detail.add(detail);
                B_Body.Tip.TipRequest.Request.RequestContent.B_Document.RecordSet.add(recordset1);
            }
        } catch (Exception e) {
            LogUtils.e("CreateParaXmlReq", "RecordSet中报文--组织报文异常");
        }
    }

    private void init(String userName, String plant, String deviceId, String appmodule,String reqType, String timestamp) {
        B_Body.Tip.TipRequest.Request.Access.A_Authentication.user = userName;
        B_Body.Tip.TipRequest.Request.Access.C_Organization.name = plant;
        B_Body.Tip.TipRequest.Request.Access.E_Appdevice.appid = deviceId;
        B_Body.Tip.TipRequest.Request.Access.E_Appdevice.appmodule = appmodule;
        B_Body.Tip.TipRequest.Request.Access.E_Appdevice.timestamp = timestamp;
        this.reqType = reqType;
    }

    public String xmlns_soapenv = "http://schemas.xmlsoap.org/soap/envelope/";

    public String xmlns_tip = "http://www.dsc.com.tw/tiptop/TIPTOPServiceGateWay";

    public String A_Header = "";

    public Body B_Body = new Body();

    public static class Body {
        public Tip Tip = new Tip();
    }

    public static class Tip {
        public TipRequest TipRequest = new TipRequest();
    }

    public static class TipRequest {
        public Request Request = new Request();
    }

    public static class Request {
        public Access Access = new Access();

        public RequestContent RequestContent = new RequestContent();
    }

    public static class Access {
        public Authentication A_Authentication = new Authentication();

        public Connection B_Connection = new Connection();

        public Organization C_Organization = new Organization();

        public Locale D_Locale = new Locale();

        public Appdevice E_Appdevice = new Appdevice();
    }


    public static class Authentication {
        @XStreamAsAttribute
        public String user = "tiptop";

        @XStreamAsAttribute
        public String password = "tiptop";
    }

    public static class Connection {
        @XStreamAsAttribute
        public String application = "APP";

        @XStreamAsAttribute
        public String source = "";
    }

    public static class Organization {
        @XStreamAsAttribute
        public String name = "SYSTEM";

    }

    public static class Locale {
        @XStreamAsAttribute
        public String language = "zh_cn";

    }

    public static class Appdevice {
        /**
         * 设备号
         */
        @XStreamAsAttribute
        public String appid = "";
        /**
         * 模组名
         */
        @XStreamAsAttribute
        public String appmodule = "A000";
        /**
         * 时间
         */
        @XStreamAsAttribute
        public String timestamp = "";
    }

    public static class RequestContent {
        public Parameter A_Parameter = new Parameter();

        public Document B_Document = new Document();
    }

    public static class Parameter {
        public Record Record = new Record();
    }

    public static class Document {
        public List<RecordSet> RecordSet = new ArrayList<RecordSet>();
    }
    @XStreamAlias("RecordSet")
    public static class RecordSet {
        @XStreamAsAttribute
        public String id = null;

        public Master A_Master = new Master();

        public List<Detail> B_Detail = new ArrayList<Detail>();

        public RecordSet(String id) {
            this.id = id;
        }
    }

    public static class Master {
        @XStreamAsAttribute
        public String name = "Master";

        public Record Record = new Record();
    }

    public static class Detail {

        @XStreamAsAttribute
        public String name = "";

        public List<Record> Record = new ArrayList<Record>();
    }

    public static class Record {
        public List<Field> Field = new ArrayList<Field>();
    }

    public static class Field {
        @XStreamAsAttribute
        public String name = "username";

        @XStreamAsAttribute
        public String value = "00001";

        public Field(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 请求数据
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String toXml() {
        LogUtils.i("CreateParaXmlReq", "start xml");
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.alias("soapenv:Envelope", CreatePurchaseCheckReq.class);
        xStream.useAttributeFor(CreatePurchaseCheckReq.class, "xmlns_soapenv");
        xStream.useAttributeFor(CreatePurchaseCheckReq.class, "xmlns_tip");
        xStream.aliasAttribute("xmlns:soapenv", "xmlns_soapenv");
        xStream.aliasAttribute("xmlns:tip", "xmlns_tip");

        xStream.aliasField("soapenv:Header", CreatePurchaseCheckReq.class, "A_Header");

        xStream.aliasField("soapenv:Body", CreatePurchaseCheckReq.class, "B_Body");
        xStream.aliasField("tip:" + reqType + "Request", Body.class, "Tip");
        xStream.aliasField("tip:request", Tip.class, "TipRequest");

        xStream.aliasField("Parameter", RequestContent.class, "A_Parameter");
        xStream.aliasField("Document", RequestContent.class, "B_Document");

        xStream.aliasField("Authentication", Access.class, "A_Authentication");
        xStream.aliasField("Connection", Access.class, "B_Connection");
        xStream.aliasField("Organization", Access.class, "C_Organization");
        xStream.aliasField("Locale", Access.class, "D_Locale");
        xStream.aliasField("Appdevice", Access.class, "E_Appdevice");

        xStream.aliasField("Master", RecordSet.class, "A_Master");
        xStream.aliasField("Detail", RecordSet.class, "B_Detail");


        xStream.addImplicitCollection(RecordSet.class, "B_Detail");
        xStream.addImplicitCollection(Document.class, "RecordSet");

        xStream.addImplicitCollection(Record.class, "Field");
        xStream.alias("Field", Field.class);
        xStream.alias("soapenv:Envelope", CreatePurchaseCheckReq.class);
        xStream.autodetectAnnotations(true);

        String xml = xStream.toXML(this);
        String xml_replace = replaceToAnd(xml);

        LogUtils.i("CreateParaXmlReq", "end xml");
        return xml_replace;
    }

    private String replaceToAnd(String origin) {
        StringBuilder builder = new StringBuilder(origin);
        int indexOfStart = builder.indexOf("<Request>");
        int indexOfEnd = builder.indexOf("</Request>");
        for (int index = indexOfStart; index <= indexOfEnd; index++) {
            if (builder.charAt(index) == '<') {
                builder.replace(index, index + 1, "&lt;");
            }
            indexOfEnd = builder.indexOf("</Request>");
        }
        LogUtils.i("CreateParaXmlReq", builder.toString());
        // .replace("\n", "")
        return builder.toString().replace("\n", "");
    }


}
