/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.client.mvp.classpage.teach.reports.studentreport;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.assessmentreport.AssessmentProgressReportChildView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.widgets.SlnCourseReportView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.widgets.SlnUnitReportView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 * 
 */
public class TeachStudentReportPopupChildView extends ChildView<TeachStudentReportPopupChildPresenter> implements IsTeachStudentReportPopupView {

	@UiField HTMLPanel reportBodyBlock;
	
	private String userId = null;
	
	private static TeachCourseReportChildViewUiBinder uiBinder = GWT.create(TeachCourseReportChildViewUiBinder.class);

	interface TeachCourseReportChildViewUiBinder extends UiBinder<Widget, TeachStudentReportPopupChildView> {
	}

	public TeachStudentReportPopupChildView(String userName, String gooruUId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.userId = gooruUId;
		setPresenter(new TeachStudentReportPopupChildPresenter(this));
		getPresenter().getStudentReportData(gooruUId);
	}
	
	@Override
	public void setReportData(ArrayList<PlanProgressDo> dataList) {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		int size = 0;
		
		if(dataList!=null) {
			size = dataList.size();
		}
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			for(int i=0;i<size;i++) {
				reportBodyBlock.add(new SlnCourseReportView(dataList.get(i),i+1));
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			for(int i=0;i<size;i++) {
				reportBodyBlock.add(new SlnUnitReportView(dataList.get(i),i+1));
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID,null);
			String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,null);
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID,null);
			String lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID,null);
			String assessmentId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID,null);
			reportBodyBlock.add(new AssessmentProgressReportChildView(assessmentId, classId, userId, courseId, unitId, lessonId));
		}
	}
}