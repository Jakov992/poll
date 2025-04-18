import React from 'react';

export interface PollOption {
    id: number;
    text: string;
    votes: {id: number, userId: string}[];
}

export interface Poll {
    id: number;
    title: string;
    description: string;
    options: PollOption[];
}

interface PollItemProps {
    poll: Poll;
    onVote: (pollId: number, optionId: number) => void;
}

const PollItem: React.FC<PollItemProps> = ({ poll, onVote }) => {
    return (
        <div className="bg-white p-4 rounded-lg shadow-md mb-4">
            <h3 className="text-xl font-bold mb-2">{poll.title}</h3>
            <p className="text-gray-700 mb-4">{poll.description}</p>
            {poll.options.map((opt) => (
                <div key={opt.id} className="flex items-center start gap-[4px] mb-2">
                    <button
                        className="mr-2 bg-blue-500 text-white px-3 py-1 rounded min-w-[60px]"
                        onClick={() => onVote(poll.id, opt.id)}
                    >
                        {opt.text}
                    </button>
                    <div>
                        Votes: {opt.votes?.length ?? 0}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default PollItem;