class Encoder {


      encryptMessage(messageToencrypt, secretkey){
        let encryptedMessage = CryptoJS.AES.encrypt(messageToencrypt, secretkey);
        return encryptedMessage.toString();
      }

      decryptMessage(encryptedMessage, secretkey){
        let decryptedBytes = CryptoJS.AES.decrypt(encryptedMessage, secretkey);
        let decryptedMessage = decryptedBytes.toString(CryptoJS.enc.Utf8);

        return decryptedMessage;
      }

      encryptMessageWithBase64(messageToencrypt, secretkey){
        let encryptedMessage = CryptoJS.AES.encrypt(messageToencrypt, secretkey);
        return window.btoa(unescape(encodeURIComponent(encryptedMessage.toString())));
      }

      decryptMessageWithBase64(encryptedMessage, secretkey){
        let toAES = decodeURIComponent(escape(window.atob(encryptedMessage)))
        let decryptedBytes = CryptoJS.AES.decrypt(toAES, secretkey);
        let decryptedMessage = decryptedBytes.toString(CryptoJS.enc.Utf8);
        return decryptedMessage;
      }


}
export default new Encoder();
