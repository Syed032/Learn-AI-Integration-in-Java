import { useEffect, useRef } from "react";
import type { Message } from "../models/Message";
import MessageBubble from "./MessageBubble";

interface Props {
  messages: Message[];
}

const ChatWindow = ({ messages }: Props) => {
  const bottomRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    bottomRef.current?.scrollIntoView({
      behavior: "smooth",
    });
  }, [messages]);

  return (
    <div className="chat-window">
      {messages.map((msg, index) => (
        <MessageBubble
          key={index}
          message={msg}
        />
      ))}

      <div ref={bottomRef}></div>
    </div>
  );
};

export default ChatWindow;