package kr.co.kfs.assetedu.model;

import lombok.Data;

@Data
public class Fnd01Fund {
	private String fnd01FundCd;
	private String fnd01FundNm;
	private String fnd01FundType;
	private String fnd01PublicCd;
	private String fnd01UnitCd;
	private String fnd01ParentCd;
	private String fnd01ParentFundCd;
	private String fnd01StartDate;
	private String fnd01EndDate;
	private Integer fnd01AccPeriod;
	private String fnd01FirstCloseDate;
	private String fnd01CurCd;
	private String fnd01KsdItemCd;
	private String fnd01KofiaCd;
	private String fnd01KofiaClassCd;
	private String fnd01FssCd;
	private String fnd01Manager;
	private String fnd01SubManager;
	private String fnd01MngCoCd;
	private String fnd01TrustCoCd;
	private String fnd01OfficeCoCd;
	private String fnd01SubOfficeCoCd;
	
	private String fnd01FundTypeNm; // 펀드타입이름
	private String fnd01PublicNm;//공모 사모 이름
	private String fnd01UnitNm;//펀드단위코드 이름
	private String fnd01ParentFundNm;//모펀드이름
	
	private String fnd01MngCoNm; //운용사이름
	private String fnd01TrustCoNm; //수탁사 이름
	private String fnd01OfficeCoNm; // 사무관리사 이름

}
