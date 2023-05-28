package com.bts.mypaint.domain.apiResponse

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.bts.mypaint.domain.dbmodel.*
import kotlinx.android.parcel.Parcelize

data class ServerDetailsResponse(

    @SerializedName("response_code")
    @Expose
    val responseCode: String,

    @SerializedName("response_message")
    @Expose
    val responseMessage: String,

    @SerializedName("database_name")
    @Expose
    val databaseName: String?,

    @SerializedName("db_server_name")
    @Expose
    val dbServerName: String?,

    @SerializedName("db_user_name")
    @Expose
    val dbUserName: String?,

    @SerializedName("db_password")
    @Expose
    val dbPassword: String?
)


data class ApiLocalLevelResponse(
    @SerializedName("post")
    @Expose
    val post: List<Post>,
    @SerializedName("contact_address")
    @Expose
    val contactAddress: List<ContactAddress>,
    @SerializedName("province")
    @Expose
    val province: List<Province>,
    @SerializedName("district")
    @Expose
    val district: List<District>,
    @SerializedName("local_level")
    @Expose
    val localLevel: List<LocalLevel>,
) {
    data class Post(
        @SerializedName("post_title")
        @Expose
        val postTitle: String,
        @SerializedName("post_date")
        @Expose
        val postDate: String,
        @SerializedName("post_summary")
        @Expose
        val postSummary: String,
        @SerializedName("post_content")
        @Expose
        val postContent: String,
        @SerializedName("post_category")
        @Expose
        val postCategory: String,
        @SerializedName("image")
        @Expose
        val image: String
    )
    data class ContactAddress(
        @SerializedName("id")
        @Expose
        val id: Int,
        @SerializedName("name")
        @Expose
        val name: String,
        @SerializedName("address")
        @Expose
        val address: String,
        @SerializedName("contact_no1")
        @Expose
        val contactNo1: String,
        @SerializedName("contact_no2")
        @Expose
        val contactNo2: String,
        @SerializedName("email")
        @Expose
        val email: String
    )
    data class District(
        @SerializedName("DISTRICT_CODE")
        @Expose
        val districtCode: String,
        @SerializedName("DISTRICT_NAME")
        @Expose
        val districtName: String,
        @SerializedName("DISTRICT_N_NAME")
        @Expose
        val districtNName: String,
        @SerializedName("ZONE_CODE")
        @Expose
        val zoneCode: String,
        @SerializedName("STATECODE")
        @Expose
        val statecode: String,
    )
    data class LocalLevel(

        @SerializedName("LEVELCODE")
        @Expose
        val levelcode: String,

        @SerializedName("LEVELNAME")
        @Expose
        val levelname: String,

        @SerializedName("CATEGORYTYPE")
        @Expose
        val categorytype: Int,

        @SerializedName("STATECODE")
        @Expose
        val statecode: String,

        @SerializedName("DISCTICTCODE")
        @Expose
        val disctictcode: Int,

        @SerializedName("WARDCOUNT")
        @Expose
        val wardcount: Int,

        @SerializedName("LFLG")
        @Expose
        val lflg: Int,

        @SerializedName("LEVELNNAME")
        @Expose
        val levelnname: String,

        @SerializedName("LEVELDESCRIPTION")
        @Expose
        val leveldescription: String,
    )
    data class Province(
        @SerializedName("STATECODE")
        @Expose
        val statecode: String,
        @SerializedName("STATENAME")
        @Expose
        val statename: String,
        @SerializedName("STATENNAME")
        @Expose
        val statenname: String,
        @SerializedName("STATEFLG")
        @Expose
        val stateflg: Int,
    )
}

data class CommonResponse(
    val status: String,
    val message: String
)



data class ColorantsResponse(
    val colorants: List<TblColorants>,
)


@Parcelize
data class RegistrationRequest(
    var name: String,
    var province: String,
    var district: String,
    var palika: String,
    var wardNo: String,
    var age: String,
    var ageType: String,
    var contact: String,
    var gender: String,
    var appoinDate: String,
    var amount: Float,
    var docCode: Int,
    var companyCode: String?,
    var departmentName: String?,
    var payType: String?,
    var docName: String?,
    var nmcNo: Int
) : Parcelable