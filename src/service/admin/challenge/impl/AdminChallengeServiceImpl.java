package service.admin.challenge.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import common.JDBCTemplate;
import dao.admin.challenge.face.AdminChallengeDao;
import dao.admin.challenge.impl.AdminChallengeDaoImpl;
import dto.Challenge;
import service.admin.challenge.face.AdminChallengeService;
import util.FileRemove;
import util.Paging;

public class AdminChallengeServiceImpl implements AdminChallengeService {
	
	private AdminChallengeDao adminChallengeDao = new AdminChallengeDaoImpl();
	private FileRemove fileRemove = new FileRemove();
	@Override
	public String getTitle(HttpServletRequest req) {
		//title 추출
		String param = req.getParameter("title");
		
		return param;
	}
	
	@Override
	public Paging getPaging(HttpServletRequest req) { //페이징 구하기
		//전달파라미터 curPage 파싱
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}

		//challenge 테이블의 총 챌린지 수를 조회한다
		int totalCount = adminChallengeDao.selectCntAll(JDBCTemplate.getConnection());

		//Paging객체 생성
		Paging paging = new Paging(totalCount, curPage);

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
		int totalCount = adminChallengeDao.selectCntAll(JDBCTemplate.getConnection(), title);

		//Paging객체 생성
		Paging paging = new Paging(totalCount, curPage);

		return paging;
	}
	
	
	@Override
	public List<Challenge> getList(Paging paging) {
		//챌린지 전체 조회 결과 처리
		return adminChallengeDao.selectAll(JDBCTemplate.getConnection(), paging);
	}
	@Override
	public List<Challenge> getList(Paging paging, String title) {
		// TODO Auto-generated method stub
		return adminChallengeDao.selectAll(JDBCTemplate.getConnection(), paging,title);
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
		Challenge result = adminChallengeDao.selectChallengeByChNo(JDBCTemplate.getConnection(), challenge);
		
		return result;
	}
	//개설자와 인증주기 제목 가져오기
	@Override
	public Map<String, String> getNameTitle(Challenge challenge) {
		Map<String, String> map = adminChallengeDao.selectNameTitle(JDBCTemplate.getConnection(), challenge);
		
		return map;
	}
	//이름을 가져온다
	@Override
	public String getName(String u_id) {
		String name = adminChallengeDao.selectName(JDBCTemplate.getConnection(), u_id);
		return name;
	}
	//카테고리 가져오기
	@Override
	public List<String> getCategory() {
		return adminChallengeDao.selectCategory(JDBCTemplate.getConnection());
	}
	//인증 주기 목록
	@Override
	public List<String> getCycle() {
		return adminChallengeDao.selectCycle(JDBCTemplate.getConnection());
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
		int chNo = adminChallengeDao.selectChNo(conn);
		challenge.setChNo(chNo);
		
		//챌린지 작성자 번호 찾아오기 
		int uNo = adminChallengeDao.selectuNo(conn, u_id); 
		challenge.setuNo(uNo);
		
		//챌린지 인증 주기 번호 찾아오기
		int cecNo = adminChallengeDao.selectCecNo(conn, chCycle);
		challenge.setCecNo(cecNo);
		
		//챌린지 카테고리 번호 찾아오기
		int caNo = adminChallengeDao.selectCaNo(conn, chCategory);
		challenge.setCaNo(caNo);
		
		//챌린지 정보가 있을 경우
		if(challenge != null) {
			//게시글 삽입
			if( adminChallengeDao.insert(conn, challenge) > 0 ) {
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
		}else {
			String path=req.getServletContext().getRealPath("upload");
			//파일 삭제 로직
			File file = new File(path+"/"+storedName);
			fileRemove.setFile(file);
			
			boolean remove = fileRemove.fileRemove(); //파일 삭제
			if(remove) {
				System.out.println("파일 삭제 성공");
			}else {
				System.out.println("파일 삭제 실패");
			}
		}
		
		Connection conn = JDBCTemplate.getConnection();
		
		//챌린지 작성자 번호 찾아오기 
		int uNo = adminChallengeDao.selectuNo(conn, u_id); 
		challenge.setuNo(uNo);

		//챌린지 인증 주기 번호 찾아오기
		int cecNo = adminChallengeDao.selectCecNo(conn, chCycle);
		challenge.setCecNo(cecNo);

		//챌린지 카테고리 번호 찾아오기
		int caNo = adminChallengeDao.selectCaNo(conn, chCategory);
		challenge.setCaNo(caNo);
		
		System.out.println(challenge);
		//챌린지 정보가 있을 경우
		if(challenge != null) {
			//게시글 삽입
			if( adminChallengeDao.update(conn, challenge) > 0 ) {
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
		challenge=adminChallengeDao.selectByStoredName(JDBCTemplate.getConnection(), challenge);
		
		
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
		
		if(adminChallengeDao.delete(JDBCTemplate.getConnection(), challenge)>0) {
			JDBCTemplate.commit(JDBCTemplate.getConnection());
		}else {
			JDBCTemplate.rollback(JDBCTemplate.getConnection());
		}
	}
}

