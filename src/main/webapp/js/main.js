var NAMESPACE_INEX = {
	hideAllDivisions : function() {
		$("#divFormQuote").hide();
		$("#divAdmin").hide();
	},
	showHome : function() {
		NAMESPACE_INEX.hideAllDivisions();

		$("#divHome").show("slow");
	}, // showHome : function
	showForm : function() {
		NAMESPACE_INEX.hideAllDivisions();

		NAMESPACE_INEX.updateForm();

		$("#divFormQuote").show("slow");
	}, // showPress:function
	showAdminForm : function() {
		NAMESPACE_INEX.hideAllDivisions();
		NAMESPACE_INEX.updateAdminForm();
	}, // showAdminForm:function()
	updateAdminForm : function() {
		$
				.ajax({
					type : "GET",
					contentType : "application/json",
					url : "rest/customer/_all",
					// url : "rest/api/v1/email/" + pCurrentFormID,
					timeout : 30000,
					// the form's
					// elements.
					success : function(data) {

						$("#tbodyCustomers").empty();

						customerLength = data.length;

						for (var index = 0; index < customerLength; index++) {
							var customerEntry = "";
							customerEntry = customerEntry
									+ "<tr id = 'customerdata_id_"
									+ data[index].customerId + "'>";
							customerEntry = customerEntry + "<td>"
									+ data[index].customerId.substring(0, 7)
									+ "</td>";
							customerEntry = customerEntry + "<td>"
									+ data[index].customerFirstName + "</td>";
							customerEntry = customerEntry + "<td>"
									+ data[index].customerLastName + "</td>";
							customerEntry = customerEntry + "<td>"
									+ data[index].customerEmail + "</td>";
							customerEntry = customerEntry + "<td>"
									+ data[index].customerPhone + "</td>";
							customerEntry = customerEntry + "<td>"
									+ data[index].customerCountry + "</td>";
							customerEntry = customerEntry + "<td>"
									+ data[index].customerDataUsageAgreement
									+ "</td>";
							customerEntry = customerEntry
									+ "<td><a href=\"#!/admin\" onClick=\"NAMESPACE_INEX.deleteCustomer('"
									+ data[index].customerId
									+"');\"><span class=\"glyphicon glyphicon-trash\"></span></a></td>";

							customerEntry = customerEntry + "</tr>";
							$("#tbodyCustomers").append(customerEntry);
						} // for

						$("#divAdmin").show("slow");
					} // success : function(data)

				});
	}, // updateAdminForm:function()
	deleteCustomer : function(customerId) {
		var postCustomerId = customerId;
		$.ajax({
			type : "DELETE",
			contentType : "application/json",
			url : "rest/customer/" + postCustomerId,
			// url : "rest/api/v1/email/" + pCurrentFormID,
			timeout : 30000,
			// the form's
			// elements.
			success : function(data) {
				var row = document.getElementById("customerdata_id_"
						+ postCustomerId);
				row.parentNode.removeChild(row);
			}
		});
	}, // deleteCustomer:function()
	updateCheckbox : function(pCheckBoxID) {
		var lCheckBox = $("#input" + pCheckBoxID);
		if (lCheckBox.prop("checked")) {
			lCheckBox.val("Accepted");

		} // if
		else {
			lCheckBox.val("Rejected");
		} // else
	}, // updateCheckbox
	validateInputField : function(pFieldName, pRejectValue) {

		if ($("#input" + pFieldName).val() != pRejectValue
				&& $("#input" + pFieldName).val() != null) {
			$("#div" + pFieldName).removeClass("has-error");
			$("#div" + pFieldName).addClass("has-success has-feedback");
			$("#span" + pFieldName + "Icon").removeClass("glyphicon-remove");
			$("#span" + pFieldName + "Icon").addClass("glyphicon-ok");

			$("#span" + pFieldName + "Icon").show();
			return true;
		} // if
		else {
			$("#div" + pFieldName).removeClass("has-success");
			$("#div" + pFieldName).addClass("has-error has-feedback");
			$("#span" + pFieldName + "Icon").removeClass("glyphicon-ok");
			$("#span" + pFieldName + "Icon").addClass("glyphicon-remove");

			$("#span" + pFieldName + "Icon").show();
			return false;
		} // else
	}, // validateInputField
	clearForm : function(pFormID) {
		$("#" + pFormID).get(0).reset();

		$(".form-control-feedback").hide();
		$(".has-success").removeClass("has-success");
		$(".has-error").removeClass("has-error");
		$(".glyphicon-ok").removeClass("glyphicon-ok");
		$(".glyphicon-remove").removeClass("glyphicon-remove");

		$("#divFormSubmitStatus").hide();

		NAMESPACE_INEX.updateCheckbox('CustomerDataUsageAgreement');

		$("html, body").animate({
			scrollTop : 0
		}, "slow");
	}, // clearForm
	updateForm : function() {
		NAMESPACE_INEX.clearForm('formQuote');
	}, // updateForm : function
	submitQuoteForm : function() {

		var lCurrentFomID = $("#divFormName").html();

		var lCurrentProgramName = "";

		var lValidData = true;

		var postData = {};

		if (NAMESPACE_INEX.validateInputField('CustomerFirstName', '') == false) {
			lValidData = false;
		} // if
		else {
			postData.customerFirstName = $("#inputCustomerFirstName").val();
		} // else

		if (NAMESPACE_INEX.validateInputField('CustomerLastName', '') == false) {
			lValidData = false;
		} // if
		else {
			postData.customerLastName = $("#inputCustomerLastName").val();
		} // else

		if (NAMESPACE_INEX.validateInputField('CustomerEmail', '') == false) {
			lValidData = false;
		} // if
		else {
			postData.customerEmail = $("#inputCustomerEmail").val();
		} // else

		if (NAMESPACE_INEX.validateInputField('CustomerCountry', '') == false) {
			lValidData = false;
		} // if
		else {
			postData.customerCountry = $("#inputCustomerCountry").val();
		} // else

		if (NAMESPACE_INEX.validateInputField('CustomerDataUsageAgreement',
				'Rejected') == false) {
			lValidData = false;
		} // if
		else {
			postData.customerDataUsageAgreement = "Agreed";
		}
		postData.customerPhone = $("#inputCustomerPhone").val();

		postData.customerAdditionalComment = $("#inputAdditionalComment").val();

		if (lValidData == true) {
			$("#buttonSubmitForm").button('loading');
			NAMESPACE_INEX.submitQuoteFormAjaxRequst(0, postData);
		} // if
		else {
			$("#customerDataUsageAgreement").prop("checked", false);
			$("#customerDataUsageAgreement").val("Rejected");

			$("#divFormSubmitStatus").addClass("alert-danger");
			$("#divFormSubmitStatus").removeClass("alert-success");

			$("#divFormSubmitStatus").empty();
			$("#divFormSubmitStatus")
					.append(
							"<p>Error: There are missing data in required fields. Please check them and try again.</p>");

			$("#divFormSubmitStatus").show();

			$("html, body").animate({
				scrollTop : 0
			}, "slow");
		} // else
	}, // submitQuoteForm:function
	submitQuoteFormAjaxRequst : function(pQueryRetryCount, pPostData) {

		var customerPostData = pPostData;

		$
				.ajax({
					type : "POST",
					contentType : "application/json",
					url : "rest/customer",
					// url : "rest/api/v1/email/" + pCurrentFormID,
					data : JSON.stringify(customerPostData), // serializes
					timeout : 30000,
					// the form's
					// elements.
					success : function(data) {
						$("#buttonSubmitForm").button('reset');
						$("#customerDataUsageAgreement").prop("checked", false);
						$("#customerDataUsageAgreement").val("Rejected");

						var formQuote = document.getElementById('formQuote');
						formQuote.reset();

						$("#divFormSubmitStatus").addClass("alert-success");
						$("#divFormSubmitStatus").removeClass("alert-danger");
						$("#divFormSubmitStatus").empty();

						var lProgramName = "";

						$("#divFormSubmitStatus")
								.append(
										"<p>Submission is success. Thanks for being interested in INEX Innovation and its products.</p>");

						NAMESPACE_INEX.clearForm('formQuote');

						$("#divFormSubmitStatus").show();
					}, // success : function
					error : function(xhr, ajaxOptions, thrownError) {
						// Submit at least twice before giving up to avoid
						// server hibernation
						var lRetryCount = 3;
						if (pQueryRetryCount < 3) {
							NAMESPACE_INEX.submitQuoteFormAjaxRequst(
									pQueryRetryCount + 1, customerPostData);
						} // if
						else {
							$("#buttonSubmitForm").button('reset');
							$("#divFormSubmitStatus").addClass("alert-danger");
							$("#divFormSubmitStatus").removeClass(
									"alert-success");

							$("#divFormSubmitStatus").empty();
							$("#divFormSubmitStatus")
									.append(
											"<p>Error: There are some problem with backend server. Please try again when you are convenient</p>");

							$("#divFormSubmitStatus").show();

							$("#customerDataUsageAgreement").prop("checked",
									false);
							$("#customerDataUsageAgreement").val("Rejected");

							$("html, body").animate({
								scrollTop : 0
							}, "slow");
						} // else

					} // error : function
				}); // $.ajax
	}, // submitQuoteFormAjaxRequst : function
	uriParser : function(targetURL) {

		parser = document.createElement('a');

		parser.href = targetURL;

		paths = parser.hash.replace('#!', '').split('?')[0].split('/');

		mainCategory = paths[1];

		if (mainCategory == "admin") {
			NAMESPACE_INEX.showAdminForm();
		} // if
		else {
			NAMESPACE_INEX.showForm();
		}
	}, // uriParser:function
}; // var NAMESPACE_INEX

/*window.onpopstate = function(event) {
	NAMESPACE_INEX.uriParser(document.location);

	return false;
}; // on
*/
NAMESPACE_INEX.uriParser(document.location);