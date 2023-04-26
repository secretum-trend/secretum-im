package com.messaging.scrtm.data.solana.entity

import com.google.gson.annotations.SerializedName

// Khai báo lớp TokenAccount để lưu trữ thông tin token của một địa chỉ ví
data class TokenAccount(
    @SerializedName("pubkey") val pubkey: String,
    @SerializedName("account") val account: TokenAccountData
)

// Khai báo lớp TokenAccountData để lưu trữ thông tin chi tiết của một token
data class TokenAccountData(
    @SerializedName("data") val data: List<Any>,
    @SerializedName("executable") val executable: Boolean,
    @SerializedName("lamports") val lamports: Long,
    @SerializedName("owner") val owner: String,
    @SerializedName("rentEpoch") val rentEpoch: Long
)

// Khai báo lớp ApiResponse để lưu trữ kết quả trả về từ API của Solana
data class ApiResponse<T>(
    @SerializedName("jsonrpc") val jsonrpc: String,
    @SerializedName("result") val result: T?,
    @SerializedName("error") val error: ApiError?
)

// Khai báo lớp ApiError để lưu trữ thông tin lỗi trả về từ API của Solana
data class ApiError(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)