# digiwin
鼎捷软件库文件
2017-06-15
    完工入库仓库可以修改
    领料过账fifo增加品名显示,调拨过账增加fifo与领料过账一致
2017-06-16
    列表标题都修改为xx列表，补料除外
2017-06-19
    报文发送多个recordset数据错误的修改
	单据fifo页面统一
	领料过账汇总界面增加fifo
	杂发扫码赋值修改为out库位
2017-06-26
	大部分模组扫码条码时增加库位传值 ,部分模块不扫库位。注意库位是取in还是out 
	barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_out_no());
	barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_in_no());
	盘点没有in或out
	快速收货A006,扫码收货A003,收货检验A004，快速入库A007
	配货复核B015，快速完工B005，完工入库B006,装箱打印B027,装箱入库B026(扫描箱号，有加库位，未加自动保存)，线边发料B002
	PQC检验A060，PQCRunCard A061,FQC检验 A062，FQCRunCard A063
	无来源调拨C010（传入拨入还是拨出）,库存查询C004，标签补打C001，库存交易锁定C012，库存交易解锁C015，产品装箱C016，产品出箱C017,调拨过账传入拨出还是拨入
	扫箱码出货D005，成品质量追溯D006
	报工全模块
	判断是否自动保存
	 saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
		if (CommonUtils.isAutoSave(saveBean)){
                                save();
                            }