package service.user.challenge.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import common.JDBCTemplate;
import dao.user.face.UserChallengeDao;
import dao.user.impl.UserChallengeDaoImpl;
import dto.Challenge;
import dto.Participation;
import dto.Payback;
import service.user.challenge.face.UserChallengeService;
import util.BankCode;
import util.FileRemove;
import util.Paging;

public class UserChallengeServiceImpl implements UserChallengeService {
	
	private UserChallengeDao userChallengeDao = new UserChallengeDaoImpl();
	private FileRemove fileRemove = new FileRemove();
	@Override
	public Challenge getCategory(HttpServletRequest req) {
		//카테고리 번호 구하기
		String param = req.getParameter("category");
		Challenge challenge = new Challenge();
		if(param!=null&&!"".equals(param)) {
			challenge.setCaNo(Integer.parseInt(param));
		}
		
		else if(req.getSession().getAttribute("category")!=null) {
			String result = String.valueOf(req.getSession().getAttribute("category"));
			challenge.setCaNo(Integer.parseInt(result));
		}
		
		return challenge;
	}
	@Override
	public String getCategory(Challenge challenge) {
		
		//카테고리 조회
		return userChallengeDao.selectCategory(JDBCTemplate.getConnection(),challenge);
	}
	
	@Override
	public String getTitle(HttpServletRequest req) {
		//title 추출
		String param = req.getParameter("title");
		
		return param;
	}
	
	@Override
	public Participation getParticipation(HttpServletRequest req) {
		//챌린지 번호와 유저 번호 추출		
		Participation participation = null;
		String chNo = req.getParameter("chNo");
		String uNo =  String.valueOf(req.getSession().getAttribute("u_no"));
		
		if(chNo!=null && !"".equals(chNo)) {
			//chNo 전달파라미터 추출
			//uNo session에서 추출
			participation=new Participation();
			participation.setChNo( Integer.parseInt(chNo) );
			participation.setuNo(Integer.parseInt(uNo));
		}
		return participation;
	}
	
	@Override
	public boolean isParticipant(Participation participation) {
		
		if(userChallengeDao.selectParticipation(JDBCTemplate.getConnection(), participation)>0) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public Paging getPaging(HttpServletRequest req) {
		//전달파라미터 curPage 파싱
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}

		//challenge 테이블의 총 챌린지 수를 조회한다
		int totalCount = userChallengeDao.selectCntAll(JDBCTemplate.getConnection());

		//Paging객체 생성 4개만 보여지도록 설정
		Paging paging = new Paging(totalCount, curPage, 4);

		return paging;
	}
	
	@Override
	public Paging getPaging(HttpServletRequest req, Challenge challenge) {
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}

		//challenge 테이블의 총 챌린지 수를 조회한다
		int totalCount = userChallengeDao.selectCntAll(JDBCTemplate.getConnection(),challenge);

		//Paging객체 생성 4개만 보여지도록 설정
		Paging paging = new Paging(totalCount, curPage, 4);

		return paging;
	}
	@Override
	public Paging getPaging(HttpServletRequest req, String title) {
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}

		//challenge 테이블의 총 챌린지 수를 조회한다
		int totalCount = userChallengeDao.selectCntAll(JDBCTemplate.getConnection(),title);

		//Paging객체 생성 4개만 보여지도록 설정
		Paging paging = new Paging(totalCount, curPage, 4);

		return paging;
	}
	@Override
	public Paging getPaging(HttpServletRequest req, Challenge challenge, String title) {
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}

		//challenge 테이블의 총 챌린지 수를 조회한다
		int totalCount = userChallengeDao.selectCntAll(JDBCTemplate.getConnection(),challenge,title);

		//Paging객체 생성 4개만 보여지도록 설정
		Paging paging = new Paging(totalCount, curPage, 4);

		return paging;
	}
	
	
	@Override
	public List<Challenge> getList(Paging paging) {
		//챌린지 전체 조회 결과 처리
		return userChallengeDao.selectAll(JDBCTemplate.getConnection(), paging);
	}
	@Override
	public List<Challenge> getList(Paging paging, Challenge challenge) {
		//챌린지 전체 조회 결과 처리 (검색 - 카테고리)
		return userChallengeDao.selectAll(JDBCTemplate.getConnection(), paging, challenge);
	}
	@Override
	public List<Challenge> getList(Paging paging, String title) {
		//챌린지 전체 조회 결과 처리 (검색 - 제목)
		
		return userChallengeDao.selectAll(JDBCTemplate.getConnection(), paging, title);
	}
	@Override
	public List<Challenge> getList(Paging paging, Challenge challenge, String title) {
		//챌린지 전체 조회 결과 처리 (검색 - 제목 - 카테고리)
		return userChallengeDao.selectAll(JDBCTemplate.getConnection(), paging, challenge, title);
	}
	@Override
	public Challenge getChallengeno(HttpServletRequest req) {
		
		//chNo를 저장할 객체 생성
		Challenge chNo = new Challenge();
		
		//chNo 전달파라미터 검증 - null, ""
		String param = req.getParameter("chNo");
		if(param!=null && !"".equals(param)) {
			//chNo 전달파라미터 추출
			chNo.setChNo( Integer.parseInt(param) );
		}
		
		//결과 객체 반환
		return chNo;
	}
	//상세 조회
	@Override
	public Challenge view(Challenge challenge) {
		Challenge result = userChallengeDao.selectChallengeByChNo(JDBCTemplate.getConnection(), challenge);

		return result;
	}
	//개설자와 인증주기 제목 가져오기
	@Override
	public Map<String, String> getNameTitle(Challenge challenge) {
		Map<String, String> map = userChallengeDao.selectNameTitle(JDBCTemplate.getConnection(), challenge);

		return map;
	}
	//이름을 가져온다
	@Override
	public String getName(String u_id) {
		String name = userChallengeDao.selectName(JDBCTemplate.getConnection(), u_id);
		return name;
	}
	//카테고리 가져오기
	@Override
	public List<String> getCategory() {
		return userChallengeDao.selectCategory(JDBCTemplate.getConnection());
	}
	//인증 주기 목록
	@Override
	public List<String> getCycle() {
		return userChallengeDao.selectCycle(JDBCTemplate.getConnection());
	}
	@Override
	public void write(HttpServletRequest req) {
		//**첨부파일 추가하여 챌린지 작성 처리
		
		String u_id = (String)req.getSession().getAttribute("u_id"); //id 가져오기
		String chCategory = null; //카테고리
		String chCycle = null; //인증 주기
		DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd"); //Date format 선언
		
		//챌린지 정보 저장할 객체
		Challenge challenge = null;
		
		//파일업로드 형태의 데이터가 맞는지 검사
		boolean isMultipart = false;
		isMultipart = ServletFileUpload.isMultipartContent(req);
		
		
		
		//multipart/form-data 인코딩으로 전송되지 않았을 경우
		if( !isMultipart ) {
			System.out.println("[ERROR] multipart/form-data 형식이 아님");
			
			return; //fileupload() 메소드 실행 중지
		}
		
		//챌린지 정보 저장할 객체 생성
		challenge = new Challenge();
	
		//디스트기반 아이템 팩토리
		DiskFileItemFactory factory = new DiskFileItemFactory();

		//메모리 처리 사이즈 지정
		factory.setSizeThreshold( 1  * 1024 ); //1KB
		
		//임시 저장소 설정
		File repository = new File(req.getServletContext().getRealPath("tmp"));
		repository.mkdir();
		
		factory.setRepository(repository);
		
		//파일업로드 객체 생성
		ServletFileUpload upload = new ServletFileUpload(factory);

		//업로드 용량 제한
		upload.setFileSizeMax( 10*1024*1024 ); //10MB
		
		//전달 데이터 파싱
		List<FileItem> items = null;
		try {
		
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		//추출된 전달파라미터 처리 반복자
		Iterator<FileItem> iter = items.iterator();

		//모든 요청 정보 처리하기
		while( iter.hasNext() ) {
			FileItem item = iter.next();
			
			// 1) 빈 파일 처리
			if( item.getSize() <= 0 )	continue;
			
			// 2) 일반적인 요청 데이터 처리
			if( item.isFormField() ) {

				String key = item.getFieldName(); //키 추출
				
				try {
					if( "ch_title".equals(key) ) { //전달파라미터 name이 "ch_title"
						challenge.setChTitle( item.getString("UTF-8") );
					} else if ( "ch_category".equals(key) ) { //전달파라미터 name이 "ch_category"
						chCategory=item.getString("UTF-8");
					}else if ( "ch_content".equals(key) ) { //전달파라미터 name이 "ch_content"
						challenge.setChContent(item.getString("UTF-8"));
					}else if ( "ch_money".equals(key) ) { //전달파라미터 name이 "ch_money"
						challenge.setChMoney(Integer.parseInt(item.getString("UTF-8")));
					}else if ( "ch_start_date".equals(key) ) { //전달파라미터 name이 "ch_start_date"
						Date startDate = dateFormat.parse(item.getString("UTF-8"));
						challenge.setChStartDate(startDate);
					}else if ( "ch_end_date".equals(key) ) { //전달파라미터 name이 "ch_end_date"
						Date endDate = dateFormat.parse(item.getString("UTF-8"));
						challenge.setChEndDate(endDate);
					}else if ( "ch_cycle".equals(key) ) { //전달파라미터 name이 "ch_cycle"
						chCycle=item.getString("UTF-8");
					}else if ( "ch_start_time".equals(key) ) { //전달파라미터 name이 "ch_start_time"
						challenge.setChStartTime(item.getString("UTF-8"));
					}else if ( "ch_end_time".equals(key) ) { //전달파라미터 name이 "ch_end_time"
						challenge.setChEndTime(item.getString("UTF-8"));
					}else if ( "ch_way".equals(key) ) { //전달파라미터 name이 "ch_way"
						challenge.setChWay(item.getString("UTF-8"));

					}// key값 비교 if end
				}
				catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				
			} // if( item.isFormField() ) end - 폼필드 확인
			
			
			// 3) 파일 처리
			if( !item.isFormField() ) {
				// java.util.Date -> String 변환 - SimpleDateFormat 클래스
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
				String rename = sdf.format(new Date());

				//확장자는 원본파일 확장자 얻어오기
				String origin = item.getName(); // 원본파일 이름
				int dotIdx = origin.lastIndexOf("."); //가장 마지막 "."의 인덱스

				//확장자
				String ext = origin.substring(dotIdx + 1);

				//서버에 저장될 파일의 이름
				String stored = rename +"."+ext;

//				System.out.println("[TEST] 원본 파일명 : "+origin);
//				System.out.println("[TEST] 저장될 파일명 : "+stored);

				// --- 첨부파일 정보 객체 ---
				challenge.setChOriginName(origin);
				challenge.setChStoredName(stored);
				// --------------------------
				
				//업로드된 임시파일을 실제로 저장하기
				// -> 1MB이하 파일 : 메모리에 임시파일로 업로드
				// -> 1MB이상 파일 : HDD에 임시파일로 업로드
				File uploadFolder = new File(req.getServletContext().getRealPath("upload")); // 업로드될 폴더 경로
				
				uploadFolder.mkdir();

				//실제로 저장될 파일
				File up = new File(uploadFolder, stored);
				
				try {
					item.write(up); //실제 업로드(최종 결과 파일이 생성됨)
					item.delete(); //임시 파일 삭제
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} // 파일 처리 end

		} // while( iter.hasNext() ) end - FileItem 반복 처리
		
		//파일이 선택되지 않았을경우
		if(challenge.getChOriginName()==null || "".equals(challenge.getChOriginName())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
			String rename = sdf.format(new Date());
			
			File uploadFolder = new File(req.getServletContext().getRealPath("upload")); // 업로드될 폴더 경로
			File defaultImg = new File(req.getServletContext().getRealPath("resources/img/AchievementWhite.png")); //이미지 파일 경로
			
			String origin = defaultImg.getName(); //파일의 본래 이름 AchievementWhite.png
			int dotIdx = origin.lastIndexOf("."); //가장 마지막 "."의 인덱스
			String ext = origin.substring(dotIdx + 1); //확장자
			String stored = rename +"."+ext; //저장 파일
			
			challenge.setChOriginName(origin);
			challenge.setChStoredName(stored);
			
			uploadFolder.mkdir();
			
			//실제로 저장될 파일
			File up = new File(uploadFolder.getPath()+"/"+stored);
			
			try {
				 FileInputStream fis = new FileInputStream(defaultImg); //읽을파일
				 FileOutputStream fos = new FileOutputStream(up); //복사할파일
				 
				 int fileByte = 0; 
		         // fis.read()가 -1 이면 파일을 다 읽은것
		         while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		         }
				 
		         //자원 사용 종료
		         fis.close(); 
		         fos.close();
		         
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
		}
		
		
		
		//DB데이터 입력
		Connection conn = JDBCTemplate.getConnection();
		
		//챌린지 번호 얻어오기
		int chNo = userChallengeDao.selectChNo(conn);
		challenge.setChNo(chNo);
		
		//챌린지 작성자 번호 찾아오기 
		int uNo = userChallengeDao.selectuNo(conn, u_id); 
		challenge.setuNo(uNo);
		
		//챌린지 인증 주기 번호 찾아오기
		int cecNo = userChallengeDao.selectCecNo(conn, chCycle);
		challenge.setCecNo(cecNo);
		
		//챌린지 카테고리 번호 찾아오기
		int caNo = userChallengeDao.selectCaNo(conn, chCategory);
		challenge.setCaNo(caNo);
		
		//챌린지 정보가 있을 경우
		if(challenge != null) {
			//챌린지 삽입
			if( userChallengeDao.insert(conn, challenge) > 0 ) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		}
		
	}

	@Override
	public void update(HttpServletRequest req) {
		
		
		String u_id = (String)req.getSession().getAttribute("u_id"); //id 가져오기
		String chCategory = null; //카테고리
		String chCycle = null; //인증 주기
		DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd"); //Date format 선언
		String storedName = null; //저장된 파일 이름
		String originName = null; //원본 파일 이름
		
		
		//챌린지 정보 저장할 객체
		Challenge challenge = null;
		
		//파일업로드 형태의 데이터가 맞는지 검사
		boolean isMultipart = false;
		isMultipart = ServletFileUpload.isMultipartContent(req);
		
		
		challenge = new Challenge();
		if(!isMultipart) {
			//파일 첨부가 선택되지 않았을 경우
			System.out.println("파일 첨부가 선택되지 않았을 경우");
			challenge.setChNo(Integer.parseInt(req.getParameter("ch_no")));
			challenge.setChTitle(req.getParameter("ch_title"));
			challenge.setChOriginName(req.getParameter("ch_origin_name"));
			challenge.setChStoredName(req.getParameter("ch_stored_name"));
			challenge.setChContent(req.getParameter("ch_content"));
			challenge.setChMoney(Integer.parseInt(req.getParameter("ch_money")));
			
			challenge.setChStartTime(req.getParameter("ch_start_time"));
			challenge.setChEndTime(req.getParameter("ch_end_time"));
			challenge.setChWay(req.getParameter("ch_way"));
			try {
				Date startDate = dateFormat.parse(req.getParameter("ch_start_date"));
				Date endDate = dateFormat.parse(req.getParameter("ch_end_date"));
				challenge.setChStartDate(startDate);
				challenge.setChEndDate(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			chCycle=req.getParameter("ch_cycle");
			chCategory=req.getParameter("ch_category");
			
		} else {
			//파일업로드를 사용하고 있을 경우
			//디스트기반 아이템 팩토리
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//메모리 처리 사이즈 지정
			factory.setSizeThreshold( 1  * 1024 ); //1KB
			
			//임시 저장소 설정
			File repository = new File(req.getServletContext().getRealPath("tmp"));
			repository.mkdir();
			
			factory.setRepository(repository);
			
			//파일업로드 객체 생성
			ServletFileUpload upload = new ServletFileUpload(factory);

			//업로드 용량 제한
			upload.setFileSizeMax( 10*1024*1024 ); //10MB
			
			//전달 데이터 파싱
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(req);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
			//추출된 전달파라미터 처리 반복자
			Iterator<FileItem> iter = items.iterator();
			
			//요청정보 처리
			while( iter.hasNext() ) {
				FileItem item = iter.next();
				
				// 빈 파일 처리
				if( item.getSize() <= 0 )	continue;
				
				// 빈 파일이 아닐 경우
				if( item.isFormField() ) {
					String key = item.getFieldName(); //키 추출
					
					try {
						if( "ch_title".equals(key) ) { //전달파라미터 name이 "ch_title"
							challenge.setChTitle( item.getString("UTF-8") );
						} else if ( "ch_category".equals(key) ) { //전달파라미터 name이 "ch_category"
							chCategory=item.getString("UTF-8");
						}else if ( "ch_content".equals(key) ) { //전달파라미터 name이 "ch_content"
							challenge.setChContent(item.getString("UTF-8"));
						}else if ( "ch_money".equals(key) ) { //전달파라미터 name이 "ch_money"
							challenge.setChMoney(Integer.parseInt(item.getString("UTF-8")));
						}else if ( "ch_start_date".equals(key) ) { //전달파라미터 name이 "ch_start_date"
							Date startDate = dateFormat.parse(item.getString("UTF-8"));
							challenge.setChStartDate(startDate);
						}else if ( "ch_end_date".equals(key) ) { //전달파라미터 name이 "ch_end_date"
							Date endDate = dateFormat.parse(item.getString("UTF-8"));	
							challenge.setChEndDate(endDate);
						}else if ( "ch_cycle".equals(key) ) { //전달파라미터 name이 "ch_cycle"
							chCycle=item.getString("UTF-8");
						}else if ( "ch_start_time".equals(key) ) { //전달파라미터 name이 "ch_start_time"
							challenge.setChStartTime(item.getString("UTF-8"));
						}else if ( "ch_end_time".equals(key) ) { //전달파라미터 name이 "ch_end_time"
							challenge.setChEndTime(item.getString("UTF-8"));
						}else if ( "ch_way".equals(key) ) { //전달파라미터 name이 "ch_way"
							challenge.setChWay(item.getString("UTF-8"));
						}else if ( "ch_origin_name".equals(key) ) { //전달파라미터 name이 "ch_way"
							originName=item.getString("UTF-8");
						}else if ( "ch_stored_name".equals(key) ) { //전달파라미터 name이 "ch_category"
							storedName=item.getString("UTF-8");
						}else if ( "ch_no".equals(key) ) { //전달파라미터 name이 "ch_category"
							challenge.setChNo(Integer.parseInt(item.getString("UTF-8")));
						}// key값 비교 if end
					}catch(UnsupportedEncodingException e) {
						e.printStackTrace();
					}catch (ParseException e) {
						e.printStackTrace();
					}
					
				} else {
					// 3) 파일 처리
					if( !item.isFormField() ) {
						// java.util.Date -> String 변환 - SimpleDateFormat 클래스
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
						String rename = sdf.format(new Date());

						//확장자는 원본파일 확장자 얻어오기
						String origin = item.getName(); // 원본파일 이름
						int dotIdx = origin.lastIndexOf("."); //가장 마지막 "."의 인덱스

						//확장자
						String ext = origin.substring(dotIdx + 1);

						//서버에 저장될 파일의 이름
						String stored = rename +"."+ext;

						System.out.println("[TEST] 원본 파일명 : "+origin);
						System.out.println("[TEST] 저장될 파일명 : "+stored);

						// --- 첨부파일 정보 객체 ---
						challenge.setChOriginName(origin);
						challenge.setChStoredName(stored);
						// --------------------------

						//업로드된 임시파일을 실제로 저장하기
						// -> 1MB이하 파일 : 메모리에 임시파일로 업로드
						// -> 1MB이상 파일 : HDD에 임시파일로 업로드
						File uploadFolder = new File(req.getServletContext().getRealPath("upload")); // 업로드될 폴더 경로

						uploadFolder.mkdir();

						//실제로 저장될 파일
						File up = new File(uploadFolder, stored);

						try {
							item.write(up); //실제 업로드(최종 결과 파일이 생성됨)
							item.delete(); //임시 파일 삭제
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} // 파일 처리 end
				} //if end
			} //while end
		} //if(!isMultipart) end
		
		//파일이 선택되지 않았을경우
		if(challenge.getChOriginName()==null || "".equals(challenge.getChOriginName())) {
			challenge.setChOriginName(originName); //기존에 있던 값을 반영
			challenge.setChStoredName(storedName); //기존에 있던 갓ㅂ을 반영
		}
		
		//챌린지 번호와 storardName을 가져온다
		Challenge removeChallenge=userChallengeDao.selectByStoredName(JDBCTemplate.getConnection(), challenge);

		String path=req.getServletContext().getRealPath("upload");
		//파일 삭제 로직
		File file = new File(path+"/"+removeChallenge.getChStoredName());
		fileRemove.setFile(file);

		boolean remove = fileRemove.fileRemove(); //파일 삭제
		if(remove) {
			System.out.println("파일 삭제 성공");
		}else {
			System.out.println("파일 삭제 실패");
		}
		
		Connection conn = JDBCTemplate.getConnection();
		
		//챌린지 작성자 번호 찾아오기 
		int uNo = userChallengeDao.selectuNo(conn, u_id); 
		challenge.setuNo(uNo);

		//챌린지 인증 주기 번호 찾아오기
		int cecNo = userChallengeDao.selectCecNo(conn, chCycle);
		challenge.setCecNo(cecNo);

		//챌린지 카테고리 번호 찾아오기
		int caNo = userChallengeDao.selectCaNo(conn, chCategory);
		challenge.setCaNo(caNo);
		
		//챌린지 정보가 있을 경우
		if(challenge != null) {
			//게시글 삽입
			if( userChallengeDao.update(conn, challenge) > 0 ) {
				JDBCTemplate.commit(conn);
				System.out.println("commit");
			} else {
				JDBCTemplate.rollback(conn);
				System.out.println("rollback");
			}
		}

	}
	@Override
	public void delete(HttpServletRequest req) {
		
		//챌린지번호 가져오기
		String param = req.getParameter("chNo");
		
		Challenge challenge = new Challenge();
		
		
		if(param != null && !"".equals(param)) {
			challenge.setChNo(Integer.parseInt(param));
		}
		//챌린지 번호와 storardName을 가져온다
		challenge=userChallengeDao.selectByStoredName(JDBCTemplate.getConnection(), challenge);
		
		
		String path=req.getServletContext().getRealPath("upload");
		//파일 삭제 로직
		File file = new File(path+"/"+challenge.getChStoredName());
		fileRemove.setFile(file);
		
		boolean remove = fileRemove.fileRemove(); //파일 삭제
		if(remove) {
			System.out.println("파일 삭제 성공");
		}else {
			System.out.println("파일 삭제 실패");
		}
		
		if(userChallengeDao.delete(JDBCTemplate.getConnection(), challenge)>0) {
			JDBCTemplate.commit(JDBCTemplate.getConnection());
		}else {
			JDBCTemplate.rollback(JDBCTemplate.getConnection());
		}
	}
	@Override
	public String getChState(Challenge challenge) {
		//챌린지 상태 반환
		return userChallengeDao.selectChState(JDBCTemplate.getConnection(), challenge);
	}
	
	@Override
	public List<Payback> getPaybList(Challenge challenge) {
		
		// 환급자들 반환
		List<Payback> paybList = userChallengeDao.selectAllPayback(JDBCTemplate.getConnection(), challenge);
		//환급자들 번호 삽입
		for(Payback payback : paybList) {
			BankCode bankCode = new BankCode(payback.getPaybRefundBank()); //은행 코드
			payback.setPaybRefundBank(bankCode.getCode()); //kg 이니시스 은행 코드로 변환
			int paybNo = userChallengeDao.selectPaybNo(JDBCTemplate.getConnection());
			payback.setPaybNo(paybNo);
		}
		//환급자들 반환
		return paybList;
	}
	@Override
	public String refundsToken() throws IOException {
		HttpURLConnection conn=null;
		URL url = new URL("https://api.iamport.kr/users/getToken"); //엑세스할 토큰을 받아올 주소 입력
		conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		
		//Data설정
		conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
		JSONObject obj = new JSONObject();
		obj.put("imp_key", "9081765440266501"); 
		obj.put("imp_secret","DdLtVjTUlJ47lCyLGOsnmwycGAlfngk0u6uqpnkK7oSs0qeHfG5BdDNTd99BqtBVA6m0tGLB5D364hOj"); 
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		bw.write(obj.toString());
		bw.flush();
		bw.close();
		
		int responseCode = conn.getResponseCode(); //응답코드 받기
		System.out.println("응답코드 : "+responseCode);
		StringBuilder sb=null;
		
		if(responseCode==200) {//성공
			//System.out.println("토큰 발급 성공!!");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			sb = new StringBuilder();
			String line = null;
			while((line=br.readLine())!=null) {
				sb.append(line+"\n");
			}
			br.close();
			System.out.println(""+sb.toString());
			
		}else {
			System.out.println(conn.getResponseMessage());
		}
		String  token= sb.toString(); //발급된 통근 반환
		return token;
	}
	@Override
	public void payback(List<Payback> paybList, String token) throws IOException {
		//최종 환급
		int responseCode; //응답 코드
		for(Payback payback : paybList) {
			URL url = new URL("https://api.iamport.kr/payments/cancel"); //환불 주소
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			JSONObject jobj = new JSONObject(token);
			
			//응답 객체
			JSONObject post1Object = jobj.getJSONObject("response");
			
			String access_token = post1Object.getString("access_token");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json"); //header 설정
			conn.setRequestProperty("Authorization", access_token); //header 설정
			conn.setRequestProperty("Accept", "application/json");
			//Data설정
			conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			JSONObject obj = new JSONObject(); //JSON 객체
			StringBuilder sb = new StringBuilder();
			
			int now = post1Object.getInt("now");
			int expired_at  = post1Object.getInt("expired_at");
		
			
			System.out.println("token(response): " + access_token);
		    System.out.println("now(response): " + now);
		    System.out.println("expired_at(response): " + expired_at);
			
			
			obj = new JSONObject();
			
			
			obj.put("imp_uid", payback.getImpUid());
			obj.put("amount", payback.getPaybAmount());
			obj.put("checksum",payback.getPaybChecksum());
			obj.put("reason",payback.getPaybReason());
			obj.put("refund_holder",payback.getPaybRefundHolder());
			obj.put("refund_bank",payback.getPaybRefundBank());
			obj.put("refund_account",payback.getPaybReFundAccount());
			
//			System.out.println("impUid:"+reMap.get(key).getImpUid());
//			System.out.println("amount:"+reMap.get(key).getReAmount());
//			System.out.println("checksum:"+reMap.get(key).getPaybChecksum());
//			System.out.println("reason:"+reMap.get(key).getPaybReason());
//			System.out.println("refund_holder:"+reMap.get(key).getPaybRefundHolder());
//			System.out.println("refund_bank:"+reMap.get(key).getPaybRefundBank());
//			System.out.println("refund_account:"+reMap.get(key).getPaybRefundAccount());
			
			
			bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(obj.toString());
			bw.flush();
			bw.close();
		    
			
			responseCode = conn.getResponseCode(); //응답코드 받기
			
			System.out.println("응답코드 : "+responseCode);
			sb=null;
			
			if(responseCode==200) {//성공
				System.out.println("환불성공!!");
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				sb = new StringBuilder();
				String line = null;
				while((line=br.readLine())!=null) {
					sb.append(line+"\n");
				}
				br.close();
				System.out.println(""+sb.toString());
				JSONObject messageObj = new JSONObject(sb.toString());
				//메시지가 있을때 출력
				
				if(!messageObj.isNull("message")) { //null인지 확인
					String message = messageObj.getString("message");
					System.out.println("message:"+message);
				}
				
			}else {
				System.out.println(conn.getResponseMessage());
			}
		
		}
	}
	@Override
	public void paybackInsert(List<Payback> paybList) {
		Connection conn = JDBCTemplate.getConnection();
		for(Payback payback : paybList) {
			if( userChallengeDao.paybInsert(conn, payback) > 0 ) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		
		}
		
	}
	@Override
	public Paging getPagingReview(HttpServletRequest req, Challenge challenge) {
		//전달파라미터 curPage 파싱
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}

		//challenge 테이블의 총 챌린지 수를 조회한다
		int totalCount = userChallengeDao.selectReviewCntAll(JDBCTemplate.getConnection(), challenge);


		Paging paging = new Paging(totalCount, curPage);

		return paging;
		
	}
	@Override
	public List<Participation> getParticipationList(Challenge challenge, Paging paging) {
		//리뷰 전체 리스트 
		return userChallengeDao.selectParticipationAll(JDBCTemplate.getConnection(), paging, challenge);
	}
	@Override
	public Participation getPaNo(HttpServletRequest req) {
		//chNo를 저장할 객체 생성
		Participation paNo = new Participation();

		//chNo 전달파라미터 검증 - null, ""
		String param = req.getParameter("paNo");
		if(param!=null && !"".equals(param)) {
			//chNo 전달파라미터 추출
			paNo.setPaNo( Integer.parseInt(param) );
		}

		//결과 객체 반환
		return paNo;
	}
	@Override
	public Participation getParticipation(Participation participation) {
		// TODO Auto-generated method stub
		return userChallengeDao.selectParticipationReview(JDBCTemplate.getConnection(), participation);
	}
	
}
