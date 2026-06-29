import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

export const sendMessage = async (message: string) => {

  const response = await axios.post(
    `${API_BASE_URL}/ai/chat`,
    {
      conversationId: "user1",
      message: message
    }
  );

  return response.data;
};