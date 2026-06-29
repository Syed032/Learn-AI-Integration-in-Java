import { useState } from "react";
import "./styles/chat.css";

import ChatWindow from "./components/ChatWindow";
import ChatInput from "./components/ChatInput";
import LoadingSpinner from "./components/LoadingSpinner";
import PdfUpload from "./components/PdfUpload";


import  type { Message } from "./models/Message";
import { sendMessage } from "./api/chatApi";



function App() {

  const [messages, setMessages] = useState<Message[]>([]);
  const [loading, setLoading] = useState(false);

  const handleSend = async (text: string) => {

    const userMessage: Message = {
      role: "user",
      content: text
    };

    setMessages(prev => [...prev, userMessage]);

    setLoading(true);

    try {

      const response = await sendMessage(text);

      const aiMessage: Message = {
        role: "assistant",
        content: response
      };

      setMessages(prev => [...prev, aiMessage]);

    } catch {

      const aiMessage: Message = {
        role: "assistant",
        content: "Error connecting to backend."
      };

      setMessages(prev => [...prev, aiMessage]);

    } finally {

      setLoading(false);

    }
  };

  return (
    <div className="app-container">

      <h1 className="title">
        Spring AI RAG Assistant
      </h1>

      <ChatWindow messages={messages} />
      {loading && <LoadingSpinner />}
      <PdfUpload />
      <ChatInput onSend={handleSend} />

      

    </div>
  );
}

export default App;