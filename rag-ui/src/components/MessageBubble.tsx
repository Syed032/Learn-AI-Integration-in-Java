import  type { Message } from "../models/Message";

interface Props {
  message: Message;
}

const MessageBubble = ({ message }: Props) => {
  return (
    <div
      className={
        message.role === "user"
          ? "message user-message"
          : "message ai-message"
      }
    >
      {message.content}
    </div>
  );
};

export default MessageBubble;