package com.bsu.bakerstreet42_ghost.tools;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import android.nfc.NdefRecord;

/**
 * 用来分析ndef record数据
 * @author fengchong
 *
 */
public class TextRecord {
	// 存储解析出来的文本
	private final String mText;

	// 不允许直接创建TextRecord对象，所以将构造方法声明为private
	private TextRecord(String text) {

		mText = text;
	}

	// 通过该方法可以获取解析出来的文本
	public String getText() {
		return mText;
	}

	// 将纯文本内容从NdefRecord对象（payload）中解析出来
	public static TextRecord parse(NdefRecord record) {
		// 验证TNF是否为NdefRecord.TNF_WELL_KNOWN
		if (record.getTnf() != NdefRecord.TNF_WELL_KNOWN)
			return null;
//		if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN)
			
		// 验证可变长度类型是否为RTD_TEXT
		if (!Arrays.equals(record.getType(), NdefRecord.RTD_TEXT))
			return null;

		try {
			// 获取payload
			byte[] payload = record.getPayload();
			// 下面代码分析payload：状态字节+ISO语言编码（ASCLL）+文本数据（UTF_8/UTF_16）
			// 其中payload[0]放置状态字节：如果bit7为0，文本数据以UTF_8格式编码，如果为1则以UTF_16编码
			// bit6是保留位，默认为0
			/*
			 * payload[0] contains the "Status Byte Encodings" field, per
			 * the NFC Forum "Text Record Type Definition" section 3.2.1.
			 * 
			 * bit7 is the Text Encoding Field.
			 * 
			 * if (Bit_7 == 0): The text is encoded in UTF-8 if (Bit_7 ==
			 * 1): The text is encoded in UTF16
			 * 
			 * Bit_6 is reserved for future use and must be set to zero.
			 * 
			 * Bits 5 to 0 are the length of the IANA language code.
			 */
			String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8"
					: "UTF-16";
			// 处理bit5-0。bit5-0表示语言编码长度（字节数）
			int languageCodeLength = payload[0] & 0x3f;
			// 获取语言编码（从payload的第2个字节读取languageCodeLength个字节作为语言编码）
			String languageCode = new String(payload, 1,
					languageCodeLength, "US-ASCII");
			// 解析出实际的文本数据
			String text = new String(payload, languageCodeLength + 1,
					payload.length - languageCodeLength - 1, textEncoding);
			
			// 创建一个TextRecord对象，并返回该对象
			return new TextRecord(text);
		} catch (UnsupportedEncodingException e) {
			// should never happen unless we get a malformed tag.
			throw new IllegalArgumentException(e);
		}
	}
}