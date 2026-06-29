import { useState } from "react";

interface Props {
  onSend: (message: string) => void;
}

const ChatInput = ({ onSend }: Props) => {

  const [input, setInput] = useState("");

  const handleSend = () => {

    if (!input.trim()) return;

    onSend(input);

    setInput("");
  };

  return (
    <div className="input-container">

      <input
        type="text"
        value={input}
        placeholder="Ask something..."
        onChange={(e) => setInput(e.target.value)}
        onKeyDown={(e) => {
          if (e.key === "Enter") {
            handleSend();
          }
        }}
      />

      <button onClick={handleSend}>
        Send
      </button>

    </div>
  );
};

export default ChatInput;