const { onCall } = require("firebase-functions/v2/https");
const midtransClient = require("midtrans-client");

// Konfigurasi Midtrans Snap
const snap = new midtransClient.Snap({
    isProduction: false, // Tetap false karena kita pakai Sandbox
    serverKey: 'SB-Mid-server-JoMSYL5Ia0BlZBasE4dIjth4',
    clientKey: 'SB-Mid-client-PGcFCDUcQntlStko'
});

// Fungsi untuk membuat transaksi QRIS
exports.createTransaction = onCall(async (request) => {
    // 1. Ambil data nominal dari aplikasi Android
    const amount = request.data.amount;
    
    // 2. Pastikan user sudah login di Firebase
    if (!request.auth) {
        return { error: "User harus login terlebih dahulu" };
    }

    const userId = request.auth.uid;

    // 3. Parameter transaksi untuk Midtrans
    const parameter = {
        "transaction_details": {
            "order_id": "ANGKOOT-" + Date.now() + "-" + userId.substring(0, 5),
            "gross_amount": parseInt(amount)
        },
        "payment_type": "qris",
        "qris": {
            "acquirer": "gopay" // Ini akan menghasilkan QRIS universal (bisa di-scan semua e-wallet)
        },
        "item_details": [{
            "id": "TOPUP01",
            "price": parseInt(amount),
            "quantity": 1,
            "name": "Top Up Saldo Angkoot"
        }]
    };

    try {
        const transaction = await snap.createTransaction(parameter);
        // Mengembalikan token dan redirect_url ke aplikasi Android
        return {
            token: transaction.token,
            redirectUrl: transaction.redirect_url
        };
    } catch (error) {
        console.error("Midtrans Error:", error);
        return { error: error.message };
    }
});